package uz.pdp.clickup.payload;

import lombok.Data;
import uz.pdp.clickup.entity.Attachment;
import uz.pdp.clickup.entity.enums.AccessTypeName;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class ProjectDto {
    @NotBlank
    private String name;

    @NotBlank
    private Long spaceId;

    @NotBlank
    private AccessTypeName accessType;

    @NotBlank
    private UUID iconId;

    @NotBlank
    private String color;
}
