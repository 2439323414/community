package life.majiang.community.service.imp;

import life.majiang.community.model.User;
import life.majiang.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createOrUpdate(User user){
        User dbUser = userRepository.findByAccountId(user.getAccountId());
        if(dbUser == null){
            //插入
            user.setGmtCreat(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreat());
            userRepository.save(user);
        }else {
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userRepository.updateDbUser(dbUser);
        }
    }
}
