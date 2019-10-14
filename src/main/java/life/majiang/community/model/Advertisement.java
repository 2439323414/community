package life.majiang.community.model;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@DynamicInsert
@DynamicUpdate
@Data
@Entity
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 256)
    private String title;
    @Column(length = 512)
    private String url;
    @Column(length = 256)
    private String img;
    @Column( name = "gmt_start")
    private Long gmtStart;
    @Column( name = "gmt_end")
    private Long gmtEnd;
    @Column( name = "gmt_creat")
    private Long gmtCreat;
    @Column( name = "gmt_modified")
    private Long gmtModified;
    private Integer status;
    @Column(nullable=false)
    private String pos;
}
