package uz.pdp.clickup.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class IconDto {
    private UUID attachmentId;
    private String color;
}
