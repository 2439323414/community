package life.majiang.community.controller;

import life.majiang.community.cache.HotTagCache;
import life.majiang.community.dto.PaginationDTO;

import life.majiang.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {



    @Autowired
    private QuestionService questionService;
    @Autowired
    private HotTagCache hotTagCache;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "10") Integer size,
                        @RequestParam(name = "search",required = false) String search,
                        @RequestParam(name = "tag",required = false) String tag){

        if (StringUtils.isBlank(search)){
            search=null;
        }
        PaginationDTO paginationDTO = questionService.listAndSearch(search,page,size,tag);
        List<String> tags = hotTagCache.getHots();
        model.addAttribute("pagination",paginationDTO);
        model.addAttribute("search",search);
        model.addAttribute("tag",tag);
        model.addAttribute("tags",tags);
        return "index";
    }
}
