package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.Workspace;
import uz.pdp.clickup.entity.WorkspaceRole;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.WorkspaceRoleDto;
import uz.pdp.clickup.repository.WorkspaceRepository;
import uz.pdp.clickup.repository.WorkspaceRoleRepository;

import java.util.Optional;

@Service
public class WorkspaceRoleServiceImpl implements WorkspaceRoleService {

    @Autowired
    WorkspaceRoleRepository workspaceRoleRepository;

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Override
    public ApiResponse addWorkspaceRole(WorkspaceRoleDto workspaceRoleDto) {

        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(workspaceRoleDto.getWorkspaceId());
        if (optionalWorkspace.isEmpty())
            return new ApiResponse("Workspace not found!", false);

        boolean exists = workspaceRoleRepository.existsByWorkspaceIdAndName(workspaceRoleDto.getWorkspaceId(), workspaceRoleDto.getName());
        if (exists)
            return new ApiResponse("WorkspaceRole exists!", false);

        WorkspaceRole workspaceRole = new WorkspaceRole(
                optionalWorkspace.get(),
                workspaceRoleDto.getName(),
                workspaceRoleDto.getExtendsRoleName()
        );

        return new ApiResponse("WorkspaceRole added!", true);
    }

}
