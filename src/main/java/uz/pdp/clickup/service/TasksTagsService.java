package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.TasksTags;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TasksTagsDto;

import java.util.List;

public interface TasksTagsService {
    ApiResponse addTasksTags(TasksTagsDto tasksTagsDto);

    ApiResponse deleteTasksTags(Long id);

    List<TasksTags> getAllTasksTagsByTaskId(Long taskId);

}
