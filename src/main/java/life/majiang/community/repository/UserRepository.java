package life.majiang.community.repository;


import life.majiang.community.modle.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    public User findByToken(String token);
}