package life.majiang.community.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "account_id",length = 100)
    private String accountId;
    @Column(length = 50)
    private String name;
    @Column(length = 36)
    private String token;
    @Column(name = "gmt_creat")
    private Long gmtCreat;
    @Column(name = "gmt_modified")
    private Long gmtModified;
    @Column(name = "avatar_url")
    private String avatarUrl;
}
