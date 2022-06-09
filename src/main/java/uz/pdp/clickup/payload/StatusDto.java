package uz.pdp.clickup.payload;

import lombok.Data;
import uz.pdp.clickup.entity.Category;
import uz.pdp.clickup.entity.Project;
import uz.pdp.clickup.entity.Space;
import uz.pdp.clickup.entity.enums.StatusTypeName;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Data
public class StatusDto {
    private String name;
    private String color;
    private Long spaceId; //status  space ga tegishli bolishi mumkin
    private Long projectId; //status  project  ga tegishli bolishi mumkin
    private Long categoryId; //status  category  ga tegishli bolishi mumkin
    private StatusTypeName statusTypeName;
    private boolean collapsed;
}
