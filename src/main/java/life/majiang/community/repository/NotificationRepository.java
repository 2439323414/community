package life.majiang.community.repository;

import life.majiang.community.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface NotificationRepository extends JpaRepository<Notification,Integer>, JpaSpecificationExecutor<Notification> {

    Long countByReceiverAndStatus(String userId,Integer status);
    @Transactional
    @Modifying
    @Query(value = "update Notification p set p.status = 1 where p.id = :id")
    void updateById(@Param("id") Integer id);
}
