package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.CategoryUser;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CategoryUserDto;
import uz.pdp.clickup.service.CategoryUserService;

import java.util.List;

@RestController
@RequestMapping("/api/categoryUser")
public class CategoryUserController {

    @Autowired
    CategoryUserService categoryUserService;

    @PostMapping
    public HttpEntity<?> addCategoryUser(@RequestBody CategoryUserDto categoryUserDto){
        ApiResponse apiResponse = categoryUserService.addCategoryUser(categoryUserDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editCategoryUser(@PathVariable Long id,
                                         @RequestBody CategoryUserDto categoryUserDto){
        ApiResponse apiResponse = categoryUserService.editCategoryUser(id, categoryUserDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCategoryUser(@PathVariable Long id){
        ApiResponse apiResponse = categoryUserService.deleteCategoryUser(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/byCategoryId/{categoryId}")
    public HttpEntity<?> getAllCategoryUsersByCategoryId(@PathVariable Long categoryId){
        List<CategoryUser> categoryUserList = categoryUserService.getAllCategoryUsersByCategoryId(categoryId);
        return ResponseEntity.ok(categoryUserList);
    }


}
