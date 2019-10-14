package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.model.Question;

import java.util.List;


public interface QuestionService {


    PaginationDTO listAndSearch(String search, Integer page, Integer size, String tag);

    PaginationDTO list(String accountId, Integer page, Integer size);

    QuestionDTO getById(Integer id);

    void creatOrUpdate(Question question);

    void incView(Integer id);

    List<QuestionDTO> selectRelated(QuestionDTO questionDTO);
}
