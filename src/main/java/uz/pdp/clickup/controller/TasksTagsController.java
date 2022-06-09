package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.TasksTags;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TasksTagsDto;
import uz.pdp.clickup.service.TasksTagsService;

import java.util.List;


@RestController
@RequestMapping("/api/tasksTags")
public class TasksTagsController {

    @Autowired
    TasksTagsService tasksTagsService;

    @PostMapping
    public HttpEntity<?> addTasksTags(@RequestBody TasksTagsDto tasksTagsDto){
        ApiResponse apiResponse = tasksTagsService.addTasksTags(tasksTagsDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteTasksTags(@PathVariable Long id){
        ApiResponse apiResponse = tasksTagsService.deleteTasksTags(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/byTaskId/{taskId}")
    public HttpEntity<?> getAllTasksTagsByTaskId(@PathVariable Long taskId){
        List<TasksTags> tasksTagsList = tasksTagsService.getAllTasksTagsByTaskId(taskId);
        return ResponseEntity.ok(tasksTagsList);
    }

}
