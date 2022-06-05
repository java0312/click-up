package uz.pdp.clickup.payload;

import lombok.Data;
import uz.pdp.clickup.entity.enums.AccessTypeName;

import java.util.UUID;

@Data
public class SpaceDto {
    private String name;
    private Long workspaceId;
    private String color;
    private AccessTypeName accessType;
    private UUID iconId;
    private UUID avatarId;
}
