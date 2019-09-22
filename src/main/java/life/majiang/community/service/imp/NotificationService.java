package life.majiang.community.service.imp;

import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.enums.NotificationStatusEnum;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.model.Notification;
import life.majiang.community.model.User;
import life.majiang.community.repository.NotificationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    public PaginationDTO list(String accountId, Integer page, Integer size) {

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(page - 1, size,sort);
        Specification<Notification> specification = new Specification<Notification>() {
            @Override
            public Predicate toPredicate(Root<Notification> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path path = root.get("receiver");
                Predicate predicate = criteriaBuilder.equal(path, accountId);
                return predicate;
            }
        };
        Page<Notification> notificationPages = notificationRepository.findAll(specification,pageRequest);
        Integer totalPage = notificationPages.getTotalPages();
        paginationDTO.setTotalPage(totalPage);
        if (page<1){
            page=1;
        }
        if (page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        paginationDTO.setPageination(totalPage,page,size);
        paginationDTO.setPage(page);
        PageRequest pageRequests =PageRequest.of(page - 1, size,sort);
        Page<Notification> questionPage = notificationRepository.findAll(specification,pageRequests);
        List<Notification> notifications = questionPage.getContent();
        if (notifications.size() == 0){
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOList);

        return paginationDTO;
    }

    public Long unreadCount(String userId) {
        Long count = notificationRepository.countByReceiverAndStatus(userId,NotificationStatusEnum.UNREAD.getStatus());
        return count;

    }

    public NotificationDTO read(Integer id, User user) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if (!notification.isPresent()){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }

        if (!Objects.equals(notification.get().getReceiver(),user.getAccountId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FALL);
        }

        notification.get().setStatus(NotificationStatusEnum.READ.getStatus());
        notificationRepository.updateById(notification.get().getId());
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification.get(),notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.get().getType()));

        return notificationDTO;
    }
}
