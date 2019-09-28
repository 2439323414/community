package life.majiang.community.repository;


import life.majiang.community.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

     User findByToken(String token);

     User findByAccountId(String accountId);

    @Transactional
    @Modifying
    @Query(value = "update User p set p.gmtModified = :#{#dbUser.gmtModified},p.avatarUrl= :#{#dbUser.avatarUrl},p.name= :#{#dbUser.name},p.token=:#{#dbUser.token} where p.accountId = :#{#dbUser.accountId}")
     void updateDbUser(@Param("dbUser") User dbUser);

    List<User> findByAccountIdIn(List<String> userIds);
}
