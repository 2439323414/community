package life.majiang.community.service.imp;

import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.repository.QuesstionRepository;
import life.majiang.community.repository.UserRepository;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<QuestionDTO> list() {
        List<Question> questions = quesstionRepository.findAll();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question: questions) {
            User user = userRepository.findByAccountId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
