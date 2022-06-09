package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.TasksUsers;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TasksUsersDto;

import java.util.List;

public interface TasksUsersService {
    ApiResponse addTasksUsers(TasksUsersDto tasksUsersDto);

    ApiResponse deleteTasksUsers(Long id);

    List<TasksUsers> getAllTasksUsersByTaskId(Long taskId);

}
