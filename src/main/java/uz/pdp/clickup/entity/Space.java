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
public class Space extends AbstractLongEntity {

    @Column(nullable = false)
    private String name;

    private String color;

    @ManyToOne
    private Workspace workspace;

    private String initialLetter;

    @ManyToOne
    private Attachment iconId;

    @ManyToOne
    private Attachment avatarId;

    @Enumerated(EnumType.STRING)
    private AccessTypeName accessType;

    @ManyToOne
    private User owner;

}
