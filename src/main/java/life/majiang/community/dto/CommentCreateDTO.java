package life.majiang.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private Integer parentId; //问题的ID
    private Integer type;
    private String content;
}
