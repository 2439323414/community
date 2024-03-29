package life.majiang.community.controller;

import life.majiang.community.cache.TagCache;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.repository.QuestionRepository;
import life.majiang.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class PublishController {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       Model model){
        Optional<Question> question = questionRepository.findById(id);
        model.addAttribute("title",question.get().getTitle());
        model.addAttribute("description",question.get().getDescription());
        model.addAttribute("tag",question.get().getTag());
        model.addAttribute("id",question.get().getId());
        return "publish";
    }
    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam(value = "id",required = false) Integer id,
            HttpServletRequest request, Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.get());
        if(title == null || title == ""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description == null || description == ""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNoneBlank(invalid)){
            model.addAttribute("error","输入非法标签"+invalid);
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getAccountId());
        question.setId(id);
        questionService.creatOrUpdate(question);

        return "redirect:/";
    }
}
