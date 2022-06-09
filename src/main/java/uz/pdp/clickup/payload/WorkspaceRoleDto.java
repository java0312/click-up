package uz.pdp.clickup.payload;

import lombok.Data;
import uz.pdp.clickup.entity.Workspace;
import uz.pdp.clickup.entity.enums.AddType;
import uz.pdp.clickup.entity.enums.WorkspacePermissionName;
import uz.pdp.clickup.entity.enums.WorkspaceRoleName;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Data
public class WorkspaceRoleDto {

    private UUID id;

    private WorkspacePermissionName permissionName;

    @NotBlank
    private Long workspaceId;

    @NotBlank
    private String name;

    @NotBlank
    private WorkspaceRoleName extendsRoleName;

    private AddType addType;

}
