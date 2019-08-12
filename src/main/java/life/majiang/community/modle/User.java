package life.majiang.community.modle;

import javax.persistence.*;

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accountId='" + accountId + '\'' +
                ", token='" + token + '\'' +
                ", gmtCreat=" + gmtCreat +
                ", gmtModified=" + gmtModified +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getGmtCreat() {
        return gmtCreat;
    }

    public void setGmtCreat(Long gmtCreat) {
        this.gmtCreat = gmtCreat;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }
}
