package uz.pdp.clickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.clickup.entity.enums.AccessTypeName;
import uz.pdp.clickup.entity.enums.TaskPermissionName;
import uz.pdp.clickup.entity.template.AbstractLongEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CategoryUser extends AbstractLongEntity {

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private TaskPermissionName taskPermissionName;

}
