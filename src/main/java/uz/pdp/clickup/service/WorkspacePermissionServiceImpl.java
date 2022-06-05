package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.WorkspacePermission;
import uz.pdp.clickup.entity.WorkspaceRole;
import uz.pdp.clickup.entity.enums.WorkspacePermissionName;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.WorkspacePermissionDto;
import uz.pdp.clickup.repository.WorkspacePermissionRepository;
import uz.pdp.clickup.repository.WorkspaceRepository;
import uz.pdp.clickup.repository.WorkspaceRoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkspacePermissionServiceImpl implements WorkspacePermissionService{
    @Autowired
    WorkspaceRoleRepository workspaceRoleRepository;

    @Autowired
    WorkspacePermissionRepository workspacePermissionRepository;

    @Override
    public ApiResponse addPermissionsToRole(WorkspacePermissionDto workspacePermissionDto) {
        Optional<WorkspaceRole> optionalWorkspaceRole = workspaceRoleRepository.findById(workspacePermissionDto.getWorkspaceRoleId());
        if (optionalWorkspaceRole.isEmpty())
            return new ApiResponse("Role not found!", false);

        List<WorkspacePermission> workspacePermissionList = new ArrayList<>();
        for (WorkspacePermissionName workspacePermissionName : workspacePermissionDto.getWorkspacePermissionNameList()) {
            workspacePermissionList.add(new WorkspacePermission(
                    optionalWorkspaceRole.get(),
                    workspacePermissionName
            ));
        }
        workspacePermissionRepository.saveAll(workspacePermissionList);

        return new ApiResponse("Permissions given", true);
    }

    @Override
    public ApiResponse deletePermissionsFromRole(WorkspacePermissionDto workspacePermissionDto) {
        Optional<WorkspaceRole> optionalWorkspaceRole = workspaceRoleRepository.findById(workspacePermissionDto.getWorkspaceRoleId());
        if (optionalWorkspaceRole.isEmpty())
            return new ApiResponse("Role not found!", false);

        List<WorkspacePermission> workspacePermissionList = workspacePermissionRepository.findAllByWorkspaceRole(optionalWorkspaceRole.get());
        workspacePermissionList.removeAll(workspacePermissionDto.getWorkspacePermissionNameList());
        workspacePermissionRepository.saveAll(workspacePermissionList);

        return new ApiResponse("Permissions taken!", true);
    }


}
