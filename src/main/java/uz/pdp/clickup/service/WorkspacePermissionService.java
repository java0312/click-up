package uz.pdp.clickup.service;

import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.WorkspacePermissionDto;

public interface WorkspacePermissionService {

    ApiResponse addPermissionsToRole(WorkspacePermissionDto workspacePermissionDto);

    ApiResponse deletePermissionsFromRole(WorkspacePermissionDto workspacePermissionDto);

}
