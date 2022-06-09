package uz.pdp.clickup.payload;

import lombok.Data;

@Data
public class CommentDto {
    private Long taskUserId;
    private Long taskId;
    private String text;
}
