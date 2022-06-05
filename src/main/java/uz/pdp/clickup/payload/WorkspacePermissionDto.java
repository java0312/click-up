package uz.pdp.clickup.payload;

import lombok.Data;
import uz.pdp.clickup.entity.enums.WorkspacePermissionName;

import java.util.List;
import java.util.UUID;

@Data
public class WorkspacePermissionDto {
    private UUID workspaceRoleId;
    private List<WorkspacePermissionName> workspacePermissionNameList;
}
