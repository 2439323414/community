package life.majiang.community.controller;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.service.QuestionService;
import life.majiang.community.service.imp.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService  commentService;

    @RequestMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){

        QuestionDTO questionDTO = questionService.getById(id);

        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> commentS = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",commentS);
        model.addAttribute("relatedQuestions",relatedQuestions);
         return "question";
    }
}
