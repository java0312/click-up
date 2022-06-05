package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.ProjectUser;
import uz.pdp.clickup.exceptions.ResourceNotFoundException;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ProjectUserDto;
import uz.pdp.clickup.repository.ProjectRepository;
import uz.pdp.clickup.repository.ProjectUserRepository;
import uz.pdp.clickup.repository.UserRepository;
import uz.pdp.clickup.repository.WorkspaceUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectUserServiceImpl implements ProjectUserService {

    @Autowired
    ProjectUserRepository projectUserRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public ApiResponse addProjectUser(ProjectUserDto projectUerDto) {
        boolean exists = projectUserRepository.existsByProjectIdAndUserId(projectUerDto.getProjectId(), projectUerDto.getUserId());
        if (exists)
            return new ApiResponse("Project already exist!", false);

        projectUserRepository.save(new ProjectUser(
                projectRepository.findById(projectUerDto.getProjectId()).orElseThrow(() -> new ResourceNotFoundException("Project not found!")),
                userRepository.findById(projectUerDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found!")),
                projectUerDto.getTaskPermission()
        ));

        return new ApiResponse("ProjectUser saved!", true);
    }

    @Override
    public ApiResponse editProjectUser(Long id, ProjectUserDto projectUerDto) {
        boolean exists = projectUserRepository.existsByProjectIdAndUserIdAndIdNot(projectUerDto.getProjectId(), projectUerDto.getUserId(), id);
        if (exists)
            return new ApiResponse("Project already exist!", false);

        Optional<ProjectUser> optionalProjectUser = projectUserRepository.findById(id);
        if (optionalProjectUser.isEmpty())
            return new ApiResponse("ProjectUser not found!", false);

        ProjectUser projectUser = optionalProjectUser.get();
        projectUser.setProject(projectRepository.findById(projectUerDto.getProjectId()).orElseThrow(() -> new ResourceNotFoundException("Project not found!")));
        projectUser.setUser(userRepository.findById(projectUerDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found!")));
        projectUser.setTaskPermission(projectUerDto.getTaskPermission());
        projectUserRepository.save(projectUser);
        return new ApiResponse("ProjectUser edited!", true);
    }

    @Override
    public ApiResponse deleteProjectUser(Long id) {
        try {
            projectUserRepository.deleteById(id);
            return new ApiResponse("ProjectUser deleted!", true);
        }catch (Exception e){
            return new ApiResponse("Error in deleting!", false);
        }
    }

    @Override
    public List<ProjectUser> getAllProjectUserByProjectId(Long projectId) {
        return projectUserRepository.findAllByProjectId(projectId);
    }

    @Override
    public ProjectUser getProjectUser(Long id) {
        Optional<ProjectUser> optionalProjectUser = projectUserRepository.findById(id);
        return optionalProjectUser.orElse(null);
    }
}
