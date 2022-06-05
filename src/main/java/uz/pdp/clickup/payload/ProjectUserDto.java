package uz.pdp.clickup.payload;

import lombok.Data;
import uz.pdp.clickup.entity.enums.TaskPermissionName;

import java.util.UUID;

@Data
public class ProjectUserDto {
    private Long projectId;
    private UUID userId;
    private TaskPermissionName taskPermission;
}
