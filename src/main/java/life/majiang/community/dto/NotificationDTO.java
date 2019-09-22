package life.majiang.community.dto;


import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private Long gmtCreate;
    private Integer status;
    private String notifier;
    private String notifierName;
    private String outerTitle;
    private String typeName;
    private Integer type;
    private Integer outerId;
}
