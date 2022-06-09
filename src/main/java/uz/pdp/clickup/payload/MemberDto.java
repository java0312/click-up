package uz.pdp.clickup.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import uz.pdp.clickup.entity.enums.AddType;
import uz.pdp.clickup.entity.enums.WorkspaceRoleName;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //null elementlarni client ga berib yubormaslik
public class MemberDto {
    private UUID id;

    private String fullName;

    private String email;

    private String roleName;

    private Timestamp lastActive;

    private UUID roleId;
    private AddType addType;

}
