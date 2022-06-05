package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.WorkspaceRoleDto;
import uz.pdp.clickup.service.WorkspaceRoleService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/workspaceRole")
public class WorkspaceRoleController {

    @Autowired
    WorkspaceRoleService workspaceRoleService;

    @PostMapping
    public HttpEntity<?> addWorkspaceRole(@Valid @RequestBody WorkspaceRoleDto workspaceRoleDto){
        ApiResponse apiResponse = workspaceRoleService.addWorkspaceRole(workspaceRoleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
