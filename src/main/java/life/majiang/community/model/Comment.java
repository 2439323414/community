package life.majiang.community.model;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@DynamicInsert
@DynamicUpdate
@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "parent_id", nullable=false)
    private Integer parentId; //问题的ID
    private Integer type;
    @Column(length = 1024)
    private String content;
    @Column(name = "commentator",length = 100)
    private String commentator;
    @Column(name = "gmt_creat")
    private Long gmtCreat;
    @Column(name = "gmt_modified")
    private Long gmtModified;
    @Column(name = "like_count", columnDefinition = "bigint default 0")
    private long likeCount;
}
