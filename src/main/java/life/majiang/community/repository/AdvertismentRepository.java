package life.majiang.community.repository;

import life.majiang.community.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertismentRepository extends JpaRepository<Advertisement,Integer> {
}
