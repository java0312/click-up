package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.ProjectUser;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ProjectUserDto;

import java.util.List;

public interface ProjectUserService {

    ApiResponse addProjectUser(ProjectUserDto projectUerDto);

    ApiResponse editProjectUser(Long id, ProjectUserDto projectUerDto);

    ApiResponse deleteProjectUser(Long id);

    List<ProjectUser> getAllProjectUserByProjectId(Long projectId);

    ProjectUser getProjectUser(Long id);

}
