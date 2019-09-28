package life.majiang.community.interceptor;

import life.majiang.community.model.Notification;
import life.majiang.community.model.User;
import life.majiang.community.repository.UserRepository;
import life.majiang.community.service.imp.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private NotificationService notificationService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0){
            for (Cookie cookie:cookies) {
                if ("token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    User user = userRepository.findByToken(token);
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                        Long unreadCount = notificationService.unreadCount(user.getAccountId());
                        request.getSession().setAttribute("unreadCount",unreadCount);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
