package life.majiang.community.repository;

import life.majiang.community.model.Comment;

import life.majiang.community.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    Optional<Comment> findByParentId(Integer id);
}
