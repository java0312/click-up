package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.*;
import uz.pdp.clickup.exceptions.ResourceNotFoundException;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CategoryDto;
import uz.pdp.clickup.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    WorkspaceUserRepository workspaceUserRepository;

    @Autowired
    CategoryUserRepository categoryUserRepository;

    @Override
    public ApiResponse addCategory(CategoryDto categoryDto) {

        //bu categoriya shu project da bormi
        boolean exists = categoryRepository.existsByNameAndProjectId(categoryDto.getName(), categoryDto.getProjectId());
        if (exists)
            return new ApiResponse("Category exists!", false);

        //project ning o'zi bormi
        Optional<Project> optionalProject = projectRepository.findById(categoryDto.getProjectId());
        if (optionalProject.isEmpty())
            return new ApiResponse("Project not found!", false);


        //category ni saqlash
        Category savedCategory = categoryRepository.save(new Category(
                categoryDto.getName(),
                optionalProject.get(),
                categoryDto.getAccessType(),
                false,
                categoryDto.getColor()
        ));


        //categoryga userlarni biriktirish
        List<CategoryUser> categoryUsers = new ArrayList<>();

        List<Long> workspaceUserIdList = categoryDto.getWorkspaceUserIdList();
        for (Long workspaceUserId : workspaceUserIdList) {
            categoryUsers.add(new CategoryUser(
                    savedCategory,
                    workspaceUserRepository.findById(workspaceUserId).orElseThrow(() -> new ResourceNotFoundException("WorkspaceUser not found!")).getUser(),
                    categoryDto.getTaskPermissionName()
            ));
        }
        categoryUserRepository.saveAll(categoryUsers);

        return new ApiResponse("Category added!", true);
    }

    @Override
    public ApiResponse editCategory(Long id, CategoryDto categoryDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty())
            return new ApiResponse("Category not found!", false);

        Optional<Project> optionalProject = projectRepository.findById(categoryDto.getProjectId());
        if(optionalProject.isEmpty())
            return new ApiResponse("Project not found!", false);


        boolean exists = categoryRepository.existsByNameAndProjectIdAndIdNot(
                categoryDto.getName(),
                categoryDto.getProjectId(),
                id
        );
        if (exists)
            return new ApiResponse("Category exists!", false);

        Category category = optionalCategory.get();
        category.setProject(optionalProject.get());
        category.setName(categoryDto.getName());
        category.setAccessType(categoryDto.getAccessType());
        category.setColor(categoryDto.getColor());
        category.setArchived(categoryDto.isArchived());
        categoryRepository.save(category);

        return new ApiResponse("Category edited!", true);
    }

    @Override
    public ApiResponse deleteCategory(Long id) {
        try {
            categoryUserRepository.deleteAllByCategoryId(id);
            categoryRepository.deleteById(id);
            return new ApiResponse("Category deleted!", true);
        }catch (Exception e){
            return new ApiResponse("Error in deleting!", false);
        }
    }

    @Override
    public List<Category> getAllCategoriesByProjectId(Long projectId) {
        return categoryRepository.findAllByProjectId(projectId);
    }

    @Override
    public Category getCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.orElse(null);
    }

}
