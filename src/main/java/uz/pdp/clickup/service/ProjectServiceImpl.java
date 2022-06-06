package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.Project;
import uz.pdp.clickup.entity.ProjectUser;
import uz.pdp.clickup.entity.Space;
import uz.pdp.clickup.entity.SpaceUser;
import uz.pdp.clickup.exceptions.ResourceNotFoundException;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ProjectDto;
import uz.pdp.clickup.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    SpaceUserRepository spaceUserRepository;

    @Autowired
    ProjectUserRepository projectUserRepository;

    @Override
    public ApiResponse addProject(ProjectDto projectDto) {

        Optional<Space> optionalSpace = spaceRepository.findById(projectDto.getSpaceId());
        if (optionalSpace.isEmpty())
            return new ApiResponse("Space not found!", false);

        boolean exists = projectRepository.existsByNameAndSpaceId(projectDto.getName(), projectDto.getSpaceId());
        if (exists)
            return new ApiResponse("Project exists!", true);

        Project savedProject = projectRepository.save(new Project(
                projectDto.getName(),
                optionalSpace.get(),
                projectDto.getAccessType(),
                projectDto.getIconId() == null ?
                        null :
                        attachmentRepository.findById(projectDto.getIconId()).orElseThrow(() -> new ResourceNotFoundException("Attachment not found!")),
                false,
                projectDto.getColor()
        ));

        //Project users
        List<ProjectUser> projectUserList = new ArrayList<>();
        for (Long id : projectDto.getSpaceUsersId()) {
            Optional<SpaceUser> optionalSpaceUser = spaceUserRepository.findById(id);
            projectUserList.add(new ProjectUser(
                   savedProject,
                   optionalSpaceUser.get().getUser(),
                    null
            ));
        }
        projectUserRepository.saveAll(projectUserList);



        return new ApiResponse("Project created!", true);
    }

    @Override
    public ApiResponse editProject(Long id, ProjectDto projectDto) {

        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isEmpty())
            return new ApiResponse("Project not found!", false);

        Optional<Space> optionalSpace = spaceRepository.findById(projectDto.getSpaceId());
        if (optionalSpace.isEmpty())
            return new ApiResponse("Space not found!", false);

        boolean exists = projectRepository.existsByNameAndSpaceId(projectDto.getName(), projectDto.getSpaceId());
        if (exists)
            return new ApiResponse("Project exists!", true);

        Project project = optionalProject.get();
        project.setName(projectDto.getName());
        project.setSpace(optionalSpace.get());
        project.setAccessType(projectDto.getAccessType());
        project.setIcon(projectDto.getIconId() == null ?
                        null :
                        attachmentRepository.findById(projectDto.getIconId()).orElseThrow(() -> new ResourceNotFoundException("Attachment not found!"))
                );
        project.setArchived(false);
        project.setColor(projectDto.getColor());
        projectRepository.save(project);

        return new ApiResponse("Project edited!", true);
    }

    @Override
    public ApiResponse deleteProject(Long id) {
        try {
            projectRepository.deleteById(id);
            return new ApiResponse("Project deleted!", true);
        }catch (Exception e){
            return new ApiResponse("Error in deleting!", false);
        }
    }

    @Override
    public List<Project> getAllProjects(Long spaceId) {
        return projectRepository.findAllBySpaceId(spaceId);
    }

    @Override
    public Project getProject(Long id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        return optionalProject.orElse(null);
    }

    @Override
    public ApiResponse archiveProject(Long id, boolean archived) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isEmpty())
            return new ApiResponse("Project not found!", false);

        Project project = optionalProject.get();
        project.setArchived(archived);
        projectRepository.save(project);
        return new ApiResponse("archived changed", true);
    }
}
