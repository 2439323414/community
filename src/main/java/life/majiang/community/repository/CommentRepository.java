package life.majiang.community.repository;

import life.majiang.community.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> findByParentIdAndTypeOrderByIdDesc(Integer id, Integer type);

    @Transactional
    @Modifying
    @Query(value = "update Comment p set p.commentCount = p.commentCount+1 where p.id = :id")
    void incCommentCount(@Param("id") Integer id);
}
