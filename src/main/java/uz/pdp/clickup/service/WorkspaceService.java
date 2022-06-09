package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.entity.Workspace;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.MemberDto;
import uz.pdp.clickup.payload.WorkspaceDto;
import uz.pdp.clickup.payload.WorkspaceRoleDto;

import java.util.List;
import java.util.UUID;

public interface WorkspaceService {

    ApiResponse addWorkspace(WorkspaceDto workspaceDto, User user);

    ApiResponse editWorkspace(Long id, WorkspaceDto workspaceDto, User user);

    ApiResponse changeOwnerWorkspace(Long id, UUID ownerId, User user);

    ApiResponse deleteWorkspace(Long id);

    ApiResponse addOrEditOrRemove(Long id, MemberDto memberDto);


    ApiResponse joinToWorkspace(Long id, User user);

    List<Workspace> getAllWorkspaces(User user);

    List<MemberDto> getMembersAndGuests(Long id);

    List<WorkspaceDto> getMyWorkspaces(User user);

    ApiResponse addOrRemovePermissionToRole(WorkspaceRoleDto workspaceRoleDto);

}
