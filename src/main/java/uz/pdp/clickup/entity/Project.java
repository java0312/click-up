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
public class Project extends AbstractLongEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Space space;

    @Enumerated(EnumType.STRING)
    private AccessTypeName accessType;

    @ManyToOne
    private Attachment icon;

    private boolean archived;

    private String color;
    
}
