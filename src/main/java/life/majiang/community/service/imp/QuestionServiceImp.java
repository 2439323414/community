package life.majiang.community.service.imp;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.repository.QuesstionRepository;
import life.majiang.community.repository.UserRepository;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImp implements QuestionService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuesstionRepository quesstionRepository;
    @Override
    public PaginationDTO list(Integer page,Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<Question> questionPages = quesstionRepository.findAll(pageRequest);
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
        PageRequest pageRequests = new PageRequest(page - 1, size);
        Page<Question> questionPage = quesstionRepository.findAll(pageRequests);
        List<Question> questions = questionPage.getContent();
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question: questions) {
            User user = userRepository.findByAccountId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }
}
