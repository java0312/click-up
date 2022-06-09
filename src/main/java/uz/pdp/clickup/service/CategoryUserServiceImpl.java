package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.Category;
import uz.pdp.clickup.entity.CategoryUser;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CategoryUserDto;
import uz.pdp.clickup.repository.CategoryRepository;
import uz.pdp.clickup.repository.CategoryUserRepository;
import uz.pdp.clickup.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryUserServiceImpl implements CategoryUserService{

    @Autowired
    CategoryUserRepository categoryUserRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ApiResponse addCategoryUser(CategoryUserDto categoryUserDto) {

        boolean exists = categoryUserRepository.existsByCategoryIdAndUserId(categoryUserDto.getCategoryId(), categoryUserDto.getUsersId());
        if (exists)
            return new ApiResponse("CategoryUser already exists!", false);

        Optional<Category> optionalCategory = categoryRepository.findById(categoryUserDto.getCategoryId());
        if (optionalCategory.isEmpty())
            return new ApiResponse("Category not found!", false);

        Optional<User> optionalUser = userRepository.findById(categoryUserDto.getUsersId());
        if (optionalUser.isEmpty())
            return new ApiResponse("User not found!", false);

        categoryUserRepository.save(new CategoryUser(
                optionalCategory.get(),
                optionalUser.get(),
                categoryUserDto.getTaskPermissionName()
        ));

        return new ApiResponse("CategoryUser saved!", true);
    }

    @Override
    public ApiResponse editCategoryUser(Long id, CategoryUserDto categoryUserDto) {
        Optional<CategoryUser> optionalCategoryUser = categoryUserRepository.findById(id);
        if (optionalCategoryUser.isEmpty())
            return new ApiResponse("CategoryUser not found!", false);

        CategoryUser categoryUser = optionalCategoryUser.get();
        categoryUser.setTaskPermissionName(categoryUserDto.getTaskPermissionName());
        categoryUserRepository.save(categoryUser);

        return new ApiResponse("CategoryUser saved!", true);
    }

    @Override
    public ApiResponse deleteCategoryUser(Long id) {
        try {
            categoryUserRepository.deleteById(id);
            return new ApiResponse("CategoryUser deleted!", true);
        }catch (Exception e){
            return new ApiResponse("Error in deleting!", false);
        }
    }

    @Override
    public List<CategoryUser> getAllCategoryUsersByCategoryId(Long categoryId) {
        return categoryUserRepository.findAllByCategoryId(categoryId);
    }
}
