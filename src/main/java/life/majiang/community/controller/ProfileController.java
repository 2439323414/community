package life.majiang.community.controller;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.model.Notification;
import life.majiang.community.model.User;
import life.majiang.community.repository.UserRepository;
import life.majiang.community.service.QuestionService;

import life.majiang.community.service.imp.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
     private NotificationService notificationService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size){

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO paginationDTO = questionService.list(user.getAccountId(), page, size);
            model.addAttribute("pagination",paginationDTO);
        }else if("replies".equals(action)){
            PaginationDTO paginationDTO = notificationService.list(user.getAccountId(), page, size);
            Long unreadCount = notificationService.unreadCount(user.getAccountId());
            model.addAttribute("section","replies");
            model.addAttribute("unreadCount",unreadCount);
            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("sectionName","最新回复");
        }
        return "profile";
    }
}