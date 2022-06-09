package uz.pdp.clickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.clickup.entity.enums.StatusTypeName;
import uz.pdp.clickup.entity.template.AbstractLongEntity;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "category_id"}))
public class Status extends AbstractLongEntity {

    @Column(nullable = false)
    private String name;

    private String color;

    @ManyToOne
    private Space space; //status  space ga tegishli bolishi mumkin

    @ManyToOne
    private Project project; //status  project  ga tegishli bolishi mumkin

    @ManyToOne
    private Category category; //status  category  ga tegishli bolishi mumkin

    @Enumerated(EnumType.STRING)
    private StatusTypeName statusTypeName;

    private boolean collapsed; //default yig'ish = false
}
