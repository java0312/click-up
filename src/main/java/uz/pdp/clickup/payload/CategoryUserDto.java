package uz.pdp.clickup.payload;

import lombok.Data;
import uz.pdp.clickup.entity.enums.TaskPermissionName;

import java.util.UUID;

@Data
public class CategoryUserDto {
    private Long categoryId;
    private UUID usersId;
    private TaskPermissionName taskPermissionName;
}
