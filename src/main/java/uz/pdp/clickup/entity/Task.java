package uz.pdp.clickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConstructorBinding;
import uz.pdp.clickup.entity.template.AbstractLongEntity;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "status_id"}))
public class Task extends AbstractLongEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Status status;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Priority priority;

    @ManyToOne
    private Task parentTask;

    private Date startedDate;

    private Time startedTimeHas;

    private Date dueDate;

    private Time dueTimeHas;

    private Integer estimateTime; //minute

    private Date activeDate;

}
