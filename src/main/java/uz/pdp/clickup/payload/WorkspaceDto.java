package uz.pdp.clickup.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class WorkspaceDto {
    @NotBlank
    private String name;

    @NotBlank
    private String color;

    private UUID avatarId;
}
