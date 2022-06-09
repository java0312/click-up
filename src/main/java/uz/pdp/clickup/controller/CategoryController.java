package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.Category;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CategoryDto;
import uz.pdp.clickup.service.CategoryService;

import java.util.List;

/**
 * Category  ochilganda shu project dagi hamma userlar u category ni ko'radi
 * Agar private deb ochsam u holda kim ko'rishini o'zim belgilayman, hammani belgilash mumkin
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public HttpEntity<?> addCategory(@RequestBody CategoryDto categoryDto){
        ApiResponse apiResponse = categoryService.addCategory(categoryDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editCategory(@PathVariable Long id,
                                      @RequestBody CategoryDto categoryDto){
        ApiResponse apiResponse = categoryService.editCategory(id, categoryDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCategory(@PathVariable Long id){
        ApiResponse apiResponse = categoryService.deleteCategory(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/byProjectId/{projectId}")
    public HttpEntity<?> getAllCategoriesByProjectId(@PathVariable Long projectId){
        List<Category> categoryList = categoryService.getAllCategoriesByProjectId(projectId);
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getCategory(@PathVariable Long id){
        Category category = categoryService.getCategory(id);
        return ResponseEntity.ok(category);
    }

}
