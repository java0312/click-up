package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.Task;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TaskDto;

import java.util.List;

public interface TaskService {

    ApiResponse addTask(TaskDto taskDto);

    ApiResponse editTask(Long id, TaskDto taskDto);

    ApiResponse deleteTask(Long id);

    List<Task> getAllTasksByCategoryId(Long categoryId);

    List<Task> getAllTasksByStatusId(Long statusId);

    ApiResponse editStatusOfTask(Long taskId, Long statusId);

}
