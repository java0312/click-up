package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.ProjectUser;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ProjectUserDto;
import uz.pdp.clickup.service.ProjectUserService;

import java.util.List;

@RestController
@RequestMapping("/api/projectUser")
public class ProjectUserController {

    @Autowired
    ProjectUserService projectUserService;

    @PostMapping
    public HttpEntity<?> addProjectUser(@RequestBody ProjectUserDto projectUerDto) {
        ApiResponse apiResponse = projectUserService.addProjectUser(projectUerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editProjectUser(@PathVariable Long id,
            @RequestBody ProjectUserDto projectUerDto) {
        ApiResponse apiResponse = projectUserService.editProjectUser(id, projectUerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProjectUser(@PathVariable Long id) {
        ApiResponse apiResponse = projectUserService.deleteProjectUser(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/byProjectId/{projectId}")
    public HttpEntity<?> getAllProjectUserByProjectId(@PathVariable Long projectId){
        List<ProjectUser> list = projectUserService.getAllProjectUserByProjectId(projectId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getProjectUser(@PathVariable Long id){
        ProjectUser projectUser = projectUserService.getProjectUser(id);
        return ResponseEntity.status(
                projectUser == null ? 409 : 200
        ).body(projectUser);
    }

}
