package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {

    private Integer id;

    private Integer parentId; //问题的ID
    private Integer type;

    private String content;

    private String commentator;

    private Long gmtCreat;

    private Long gmtModified;

    private long likeCount;
    private long commentCount;
    private User user;
}
