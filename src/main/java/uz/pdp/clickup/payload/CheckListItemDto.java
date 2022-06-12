package uz.pdp.clickup.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class CheckListItemDto {
    private String name;
    private Long checkListId;
    private boolean resolved;
    private UUID assignUserId;
}
