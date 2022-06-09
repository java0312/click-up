package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.Category;
import uz.pdp.clickup.entity.Project;
import uz.pdp.clickup.entity.Space;
import uz.pdp.clickup.entity.Status;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.StatusDto;
import uz.pdp.clickup.repository.CategoryRepository;
import uz.pdp.clickup.repository.ProjectRepository;
import uz.pdp.clickup.repository.SpaceRepository;
import uz.pdp.clickup.repository.StatusRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    SpaceRepository spaceRepository;

    @Override
    public ApiResponse addStatus(StatusDto statusDto) {

        boolean exists = statusRepository.existsByNameAndCategoryId(statusDto.getName(), statusDto.getCategoryId());
        if (exists)
            return new ApiResponse("Status already exists!", false);

        Status status = new Status(
                statusDto.getName(),
                statusDto.getColor(),
                null,
                null,
                null,
                statusDto.getStatusTypeName(),
                statusDto.isCollapsed()
        );

        if (statusDto.getCategoryId() != null) {
            Optional<Category> optionalCategory = categoryRepository.findById(statusDto.getCategoryId());
            optionalCategory.ifPresent(status::setCategory);
        } else if (statusDto.getProjectId() != null) {
            Optional<Project> optionalProject = projectRepository.findById(statusDto.getProjectId());
            optionalProject.ifPresent(status::setProject);
        }else if (statusDto.getSpaceId() != null){
            Optional<Space> optionalSpace = spaceRepository.findById(statusDto.getSpaceId());
            optionalSpace.ifPresent(status::setSpace);
        }else {
            return new ApiResponse("Choose category", false);
        }

        statusRepository.save(status);

        return new ApiResponse("Status added!", true);
    }

    @Override
    public ApiResponse editStatus(Long id, StatusDto statusDto) {

        Optional<Status> optionalStatus = statusRepository.findById(id);
        if (optionalStatus.isEmpty())
            return new ApiResponse("Status not found!", false);

        boolean exist = statusRepository.existsAllByNameAndCategoryIdAndIdNot(statusDto.getName(), statusDto.getCategoryId(), id);
        if (exist)
            return new ApiResponse("This status already exists!", true);

        Status status = optionalStatus.get();
        status.setName(statusDto.getName());
        status.setCollapsed(statusDto.isCollapsed());
        status.setColor(statusDto.getColor());
        statusRepository.save(status);

        return new ApiResponse("Status edited!", true);
    }

    @Override
    public ApiResponse deleteStatus(Long id) {
        try {
            statusRepository.deleteById(id);
            return new ApiResponse("Status deleted!", false);
        }catch (Exception e){
            return new ApiResponse("Error in deleting!", false);
        }
    }

    @Override
    public List<Status> getAllStatusByCategoryId(Long categoryId) {
        return statusRepository.findAllByCategoryId(categoryId);
    }

}
