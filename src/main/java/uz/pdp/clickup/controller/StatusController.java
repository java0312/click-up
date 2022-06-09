package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.Status;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.StatusDto;
import uz.pdp.clickup.service.StatusService;

import java.util.List;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Autowired
    StatusService statusService;

    @PostMapping
    public HttpEntity<?> addStatus(@RequestBody StatusDto statusDto){
        ApiResponse apiResponse = statusService.addStatus(statusDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editStatus(@PathVariable Long id,
                                    @RequestBody StatusDto statusDto){
        ApiResponse apiResponse = statusService.editStatus(id, statusDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteStatus(@PathVariable Long id){
        ApiResponse apiResponse = statusService.deleteStatus(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/byCategoryId/{categoryId}")
    public HttpEntity<?> getAllStatusByCategoryId(@PathVariable Long categoryId){
        List<Status> statusList = statusService.getAllStatusByCategoryId(categoryId);
        return ResponseEntity.ok(statusList);
    }

}
