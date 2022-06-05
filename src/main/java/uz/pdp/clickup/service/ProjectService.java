package uz.pdp.clickup.service;

import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.Project;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ProjectDto;

import java.util.List;

public interface ProjectService {

    ApiResponse addProject(ProjectDto projectDto);

    ApiResponse editProject(Long id, ProjectDto projectDto);

    ApiResponse deleteProject(Long id);

    List<Project> getAllProjects(Long spaceId);

    Project getProject(Long id);

    ApiResponse archiveProject(Long id, boolean archived);
}
