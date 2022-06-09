package uz.pdp.clickup.payload;

import lombok.Data;

@Data
public class TagDto {
    private String name;
    private String color;
    private Long workspaceId;

    //for tag_task
    private Long taskId;
}
