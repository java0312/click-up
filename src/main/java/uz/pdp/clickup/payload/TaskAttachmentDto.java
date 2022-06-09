package uz.pdp.clickup.payload;

import lombok.Data;

@Data
public class TaskAttachmentDto {
    private Long taskId;
    private boolean pinCoverImage;
}
