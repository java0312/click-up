package uz.pdp.clickup.payload;

import lombok.Data;
import uz.pdp.clickup.entity.Project;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.entity.enums.AccessTypeName;
import uz.pdp.clickup.entity.enums.TaskPermissionName;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.util.List;

@Data
public class CategoryDto {
    private String name;

    private Long projectId;

    private AccessTypeName accessType;

    private String color;

    /*
    * Bu yerda Workspacedagi userlarning idlari bo'lishi mumkin
    * va shu workspace dan tashqaridagi userlarning ham id lari bo'lishi mumkin va ular taklif qilinadi
    * */
    private List<Long> workspaceUserIdList;

    private TaskPermissionName taskPermissionName;

    private boolean archived;
}
