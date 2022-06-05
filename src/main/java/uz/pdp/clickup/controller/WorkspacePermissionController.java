package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.WorkspacePermissionDto;
import uz.pdp.clickup.service.WorkspacePermissionService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/workspacePermission")
public class WorkspacePermissionController {

    @Autowired
    WorkspacePermissionService workspacePermissionService;

    /**
     * Biror rolga permission lar berish
     */
    @PostMapping
    public HttpEntity<?> addPermissionsToRole(@Valid @RequestBody WorkspacePermissionDto workspacePermissionDto){
        ApiResponse apiResponse = workspacePermissionService.addPermissionsToRole(workspacePermissionDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /*
    * Biror roldan permission larni olib tashlash
    * */
    @DeleteMapping
    public HttpEntity<?> deletePermissionsFromRole(@Valid @RequestBody WorkspacePermissionDto workspacePermissionDto){
        ApiResponse apiResponse = workspacePermissionService.deletePermissionsFromRole(workspacePermissionDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
