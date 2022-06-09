package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.Category;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CategoryDto;

import java.util.List;

public interface CategoryService {

    ApiResponse addCategory(CategoryDto categoryDto);

    ApiResponse editCategory(Long id, CategoryDto categoryDto);

    ApiResponse deleteCategory(Long id);

    List<Category> getAllCategoriesByProjectId(Long projectId);

    Category getCategory(Long id);

}
