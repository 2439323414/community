package life.majiang.community.model;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@DynamicInsert
@DynamicUpdate
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50)
    private String title;
    @Column(columnDefinition = "text")
    private String description;
    @Column(name = "gmt_creat")
    private Long gmtCreat;
    @Column(name = "gmt_modified")
    private Long gmtModified;
    private String creator;
    @Column(name = "comment_count", columnDefinition = "int default 0")
    private Integer commentCount;
    @Column(name = "view_count", columnDefinition = "int default 0")
    private Integer viewCount;
    @Column(name = "like_count", columnDefinition = "int default 0")
    private Integer likeCount;
    private String tag;
}
