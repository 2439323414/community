package life.majiang.community.repository;

import life.majiang.community.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    Optional<Comment> findByParentId(Integer id);

    List<Comment> findByParentIdAndType(Integer id, Integer type);
}
