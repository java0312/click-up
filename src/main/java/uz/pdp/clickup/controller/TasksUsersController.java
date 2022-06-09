package uz.pdp.clickup.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.TasksUsers;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TasksUsersDto;
import uz.pdp.clickup.repository.TasksUsersRepository;
import uz.pdp.clickup.service.TasksUsersService;

import java.util.List;

@RestController
@RequestMapping("/api/tasksUsers")
public class TasksUsersController {
    
    @Autowired
    TasksUsersService tasksUsersService;

    @PostMapping
    public HttpEntity<?> addTasksUsers(@RequestBody TasksUsersDto tasksUsersDto){
        ApiResponse apiResponse = tasksUsersService.addTasksUsers(tasksUsersDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteTasksUsers(@PathVariable Long id){
        ApiResponse apiResponse = tasksUsersService.deleteTasksUsers(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/byTaskId/{taskId}")
    public HttpEntity<?> getAllTasksUsersByTaskId(@PathVariable Long taskId){
        List<TasksUsers> tasksUsersList = tasksUsersService.getAllTasksUsersByTaskId(taskId);
        return ResponseEntity.ok(tasksUsersList);
    }
    
}
