package life.majiang.community.model;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@DynamicInsert
@DynamicUpdate
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 100,nullable=false)
    private String notifier;
    @Column(length = 100,nullable=false)
    private String receiver;
    @Column(name = "outer_id",nullable=false)
    private Integer outerId;
    @Column(nullable=false)
    private Integer type;
    @Column(name = "gmt_creat")
    private Long gmtCreat;
    @Column(columnDefinition = "int default 0")
    private Integer status;
    @Column(name = "notifier_name",length = 100)
    private String notifierName;
    @Column(name = "outer_title",length = 256)
    private String outerTitle;
}
