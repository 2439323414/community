package life.majiang.community.repository;

import life.majiang.community.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuesstionRepository extends JpaRepository<Question,Integer> {
}
