package life.majiang.community.modle;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

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
    private Integer creator;
    @Column(name = "comment_count", columnDefinition = "int default 0")
    private Integer commentCount;
    @Column(name = "view_count", columnDefinition = "int default 0")
    private Integer viewCount;
    @Column(name = "like_count", columnDefinition = "int default 0")
    private Integer likeCount;
    private String tag;

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", gmtCreat=" + gmtCreat +
                ", gmtModified=" + gmtModified +
                ", creator=" + creator +
                ", commentCount=" + commentCount +
                ", viewCount=" + viewCount +
                ", likeCount=" + likeCount +
                ", tag='" + tag + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
