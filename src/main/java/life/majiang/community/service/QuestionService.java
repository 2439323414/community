package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;


public interface QuestionService {


    PaginationDTO list(Integer page, Integer size);

    PaginationDTO list(String accountId, Integer page, Integer size);
}
