package uz.pdp.clickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.clickup.entity.template.AbstractLongEntity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TasksUsers extends AbstractLongEntity {

    @ManyToOne
    private Task task;

    @ManyToOne
    private User user;

}
