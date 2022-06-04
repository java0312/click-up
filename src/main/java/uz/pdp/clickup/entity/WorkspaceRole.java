package uz.pdp.clickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.clickup.entity.enums.WorkspaceRoleName;
import uz.pdp.clickup.entity.template.AbstractUUIDEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkspaceRole extends AbstractUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Workspace workspace;

//    private WorkspaceRoleName roleName;
    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private WorkspaceRoleName extendsRole;



}
