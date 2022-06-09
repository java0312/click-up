package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.CategoryUser;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CategoryUserDto;

import java.util.List;

public interface CategoryUserService {
    ApiResponse addCategoryUser(CategoryUserDto categoryUserDto);

    ApiResponse editCategoryUser(Long id, CategoryUserDto categoryUserDto);

    ApiResponse deleteCategoryUser(Long id);

    List<CategoryUser> getAllCategoryUsersByCategoryId(Long categoryId);


}
