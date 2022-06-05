package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.Project;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ProjectDto;
import uz.pdp.clickup.security.CurrentUser;
import uz.pdp.clickup.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PutMapping("/archive/{id}")
    public HttpEntity<?> archiveProject(@RequestParam boolean archived,
                                        @PathVariable Long id){
        ApiResponse apiResponse = projectService.archiveProject(id, archived);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping
    public HttpEntity<?> addProject(@RequestBody ProjectDto projectDto){
        ApiResponse apiResponse = projectService.addProject(projectDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editProject(@PathVariable Long id, @RequestBody ProjectDto projectDto){
        ApiResponse apiResponse = projectService.editProject(id, projectDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProject(@PathVariable Long id){
        ApiResponse apiResponse = projectService.deleteProject(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/bySpaceId/{spaceId}")
    public HttpEntity<?> getAllProjects(@PathVariable Long spaceId){
        List<Project> projects = projectService.getAllProjects(spaceId);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getProject(@PathVariable Long id){
        Project project = projectService.getProject(id);
        return ResponseEntity.status(project != null ? 200 : 409).body(project);
    }

}
