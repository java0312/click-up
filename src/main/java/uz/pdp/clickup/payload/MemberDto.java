package uz.pdp.clickup.payload;

import lombok.Data;
import uz.pdp.clickup.entity.enums.AddType;
import uz.pdp.clickup.entity.enums.WorkspaceRoleName;

import java.util.UUID;

@Data
public class MemberDto {
    private UUID id;
    private UUID roleId;
    private AddType addType;

}
