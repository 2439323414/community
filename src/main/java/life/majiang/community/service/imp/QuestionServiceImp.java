package life.majiang.community.service.imp;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.repository.QuestionRepository;
import life.majiang.community.repository.UserRepository;
import life.majiang.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Lists;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImp implements QuestionService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Override
    public PaginationDTO listAndSearch(String search,Integer page, Integer size) {
        if (StringUtils.isNotBlank(search)){
             search = StringUtils.replace(search, " ", "|");
        }else {
            search =null;
        }

        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequest pageRequest =PageRequest.of(page - 1, size,sort);
        Page<Question> questionPages;
        if (search==null){
             questionPages = questionRepository.findAll(pageRequest);
        }else {
            String finalSearch = search;
            Specification<Question> specification = new Specification<Question>() {
                @Override
                public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path path = root.get("title");
                    List<Predicate> predicates = new ArrayList<>();
                    String[] splits = finalSearch.split("\\|");
                    for (String split :splits) {
                        predicates.add(criteriaBuilder
                                .like(path, "%" + split + "%"));
                    }
                    Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                    return predicate;
                }
            };
            questionPages = questionRepository.findAll(specification,pageRequest);
        }
        Integer totalPage = questionPages.getTotalPages();
        paginationDTO.setTotalPage(totalPage);
        if (page<1){
            page=1;
        }
        if (page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        paginationDTO.setPageination(totalPage,page,size);
        paginationDTO.setPage(page);
        PageRequest pageRequests =PageRequest.of(page - 1, size,sort);
        Page<Question> questionPage;
        if (search==null){
            questionPage = questionRepository.findAll(pageRequest);
        }else {
            String finalSearch = search;
            Specification<Question> specification = new Specification<Question>() {
                @Override
                public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path path = root.get("title");
                    List<Predicate> predicates = new ArrayList<>();
                    String[] splits = finalSearch.split("\\|");
                    for (String split :splits) {
                        predicates.add(criteriaBuilder
                                .like(path, "%" + split + "%"));
                    }
                    Predicate predicate = criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
                    return predicate;
                }
            };
            questionPage = questionRepository.findAll(specification,pageRequest);
        }
        List<Question> questions = questionPage.getContent();
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question: questions) {
            User user = userRepository.findByAccountId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    @Override
    public PaginationDTO list(String accountId, Integer page, Integer size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(page - 1, size,sort);
        Specification<Question> specification = new Specification<Question>() {
            @Override
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path path = root.get("creator");
                Predicate predicate = criteriaBuilder.equal(path, accountId);
                return predicate;
            }
        };
        Page<Question> questionPages = questionRepository.findAll(specification,pageRequest);
        Integer totalPage = questionPages.getTotalPages();
        paginationDTO.setTotalPage(totalPage);
        if (page<1){
            page=1;
        }
        if (page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        paginationDTO.setPageination(totalPage,page,size);
        paginationDTO.setPage(page);
        PageRequest pageRequests =PageRequest.of(page - 1, size,sort);
        Page<Question> questionPage = questionRepository.findAll(specification,pageRequests);
        List<Question> questions = questionPage.getContent();
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question: questions) {
            User user = userRepository.findByAccountId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    @Override
    public QuestionDTO getById(Integer id) {
        Optional<Question> question = questionRepository.findById(id);
        if(!question.isPresent()){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userRepository.findByAccountId(question.get().getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question.get(),questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void creatOrUpdate(Question question) {
        if (question.getId() == null){
            //创建
            question.setGmtCreat(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreat());
            question.setCommentCount(0);
            question.setLikeCount(0);
            question.setViewCount(0);
            questionRepository.save(question);
        }else {
            //更新
            question.setGmtModified(System.currentTimeMillis());
            questionRepository.updateQuestion(question);

        }
    }

    @Override
    public void incView(Integer id) {
        questionRepository.updateIncView(id);
    }

    @Override
    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if (StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }
        String replace = StringUtils.replace(questionDTO.getTag(), ",", "|");
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(replace);
        List<Question> questions = questionRepository.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO1);
            return questionDTO1;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
