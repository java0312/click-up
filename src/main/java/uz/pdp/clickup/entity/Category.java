package uz.pdp.clickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.clickup.entity.enums.AccessTypeName;
import uz.pdp.clickup.entity.template.AbstractLongEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends AbstractLongEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Project project;

    @Enumerated(EnumType.STRING)
    private AccessTypeName accessType;

    private boolean archived;

    private String color;

}
