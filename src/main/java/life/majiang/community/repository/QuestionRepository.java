package life.majiang.community.repository;

import life.majiang.community.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Integer>, JpaSpecificationExecutor<Question> {
    public Optional<Question> findById(Integer id);



    @Transactional
    @Modifying
    @Query(value = "update Question p set p.gmtModified = :#{#question.gmtModified},p.title= :#{#question.title},p.description= :#{#question.description},p.tag=:#{#question.tag} where p.id = :#{#question.id}")
    void updateQuestion(@Param("question") Question question);

    @Transactional
    @Modifying
    @Query(value = "update Question p set p.viewCount = p.viewCount+1 where p.id = :id")
    void updateIncView(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query(value = "update Question p set p.commentCount = p.commentCount+1 where p.id = :id")
    void incCommentCount(@Param("id") Integer id);


}
