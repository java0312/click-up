package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.Task;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TaskDto;
import uz.pdp.clickup.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping
    public HttpEntity<?> addTask(@RequestBody TaskDto taskDto) {
        ApiResponse apiResponse = taskService.addTask(taskDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editTask(@PathVariable Long id,
                                  @RequestBody TaskDto taskDto) {
        ApiResponse apiResponse = taskService.editTask(id, taskDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteTask(@PathVariable Long id) {
        ApiResponse apiResponse = taskService.deleteTask(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/byCategoryId/{categoryId}")
    public HttpEntity<?> getAllTasksByCategoryId(@PathVariable Long categoryId) {
        List<Task> taskList = taskService.getAllTasksByCategoryId(categoryId);
        return ResponseEntity.ok(taskList);
    }

    @GetMapping("/byStatusId/{statusId}")
    public HttpEntity<?> getAllTasksByStatusId(@PathVariable Long statusId) {
        List<Task> taskList = taskService.getAllTasksByStatusId(statusId);
        return ResponseEntity.ok(taskList);
    }


    @PutMapping("/changeStatus/{taskId}")
    public HttpEntity<?> editStatusOfTask(@PathVariable Long taskId, @RequestParam Long statusId) {
        ApiResponse apiResponse = taskService.editStatusOfTask(taskId, statusId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


}
