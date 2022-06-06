package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.Icon;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.IconDto;
import uz.pdp.clickup.service.IconService;

import java.util.List;

@RestController
@RequestMapping("/api/icon")
public class IconController {

    @Autowired
    IconService iconService;

    @PostMapping
    public HttpEntity<?> addIcon(@RequestBody IconDto iconDto){
        ApiResponse apiResponse = iconService.addIcon(iconDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editIcon(@PathVariable Long id,
                                  @RequestBody IconDto iconDto){
        ApiResponse apiResponse = iconService.editIcon(id, iconDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteIcon(@PathVariable Long id){
        ApiResponse apiResponse = iconService.deleteIcon(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAllIcons(){
        List<Icon> icons = iconService.getAllIcons();
        return ResponseEntity.ok(icons);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getIcon(@PathVariable Long id){
        Icon icon = iconService.getIcon(id);
        return ResponseEntity.ok(icon);
    }

}
