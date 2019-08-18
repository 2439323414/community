package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.model.Question;


public interface QuestionService {


    PaginationDTO list(Integer page, Integer size);

    PaginationDTO list(String accountId, Integer page, Integer size);

    QuestionDTO getById(Integer id);

    void creatOrUpdate(Question question);
}
