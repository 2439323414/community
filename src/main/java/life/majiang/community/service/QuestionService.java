package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;


public interface QuestionService {


    PaginationDTO list(Integer page, Integer size);
}
