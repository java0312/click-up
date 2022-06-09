package uz.pdp.clickup.payload;

import lombok.Data;
import uz.pdp.clickup.entity.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
public class TaskDto {

    private String name;
    private Long statusId;
    private String description;

    private Long categoryId;
    private Long priorityId;
    private Long parentTaskId;

    private Date startedDate;
    private Time startedTimeHas;
    private Date dueDate;
    private Time dueTimeHas;
    private Integer estimateTime;

    private List<UUID> usersIdList;
}
