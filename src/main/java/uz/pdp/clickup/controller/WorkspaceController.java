package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.entity.Workspace;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.MemberDto;
import uz.pdp.clickup.payload.WorkspaceDto;
import uz.pdp.clickup.payload.WorkspaceRoleDto;
import uz.pdp.clickup.security.CurrentUser;
import uz.pdp.clickup.service.WorkspaceService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspace")
public class WorkspaceController {

    @Autowired
    WorkspaceService workspaceService;


    @GetMapping("/my")
    public HttpEntity<?> getAllWorkspaces(@CurrentUser User user){
        List<Workspace> workspaces = workspaceService.getAllWorkspaces(user);
        return ResponseEntity.ok(workspaces);
    }


    @PostMapping
    public HttpEntity<?> addWorkspace(@Valid @RequestBody WorkspaceDto workspaceDto, @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.addWorkspace(workspaceDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * Name, color, avatar o'zgarishi mumkin
     *
     * @param id
     * @param workspaceDto
     * @return
     */
    @PutMapping("/{id}")
    public HttpEntity<?> editWorkspace(@PathVariable Long id,
                                       @Valid @RequestBody WorkspaceDto workspaceDto,
                                       @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.editWorkspace(id, workspaceDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * Ishxona egasi boshqa bo'ladi
     *
     * @param id
     * @param ownerId
     * @return
     */
    @PutMapping("/changeOwner/{id}")
    public HttpEntity<?> changeOwnerWorkspace(@PathVariable Long id,
                                              @RequestParam UUID ownerId,
                                              @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.changeOwnerWorkspace(id, ownerId, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * Ishxonani o'chirish
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteWorkspace(@PathVariable Long id) {
        ApiResponse apiResponse = workspaceService.deleteWorkspace(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/addOrEditOrRemove/{id}")
    public HttpEntity<?> addOrEditOrRemove(@PathVariable Long id,
                                           @Valid @RequestBody MemberDto memberDto){
        ApiResponse apiResponse = workspaceService.addOrEditOrRemove(id, memberDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /*
    * Emailga kelgan ishxonaga taklifni tasdiqlash
    * */

    @PutMapping("/join")
    public HttpEntity<?> joinToWorkspace(@RequestParam Long id, @CurrentUser User user){
        ApiResponse apiResponse = workspaceService.joinToWorkspace(id, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * LAST LESSON
     *
     */

    //id = workspaceId
    @GetMapping("/member/{id}")
    public HttpEntity<?> getMembersAndGuests(@PathVariable Long id){
        List<MemberDto> members = workspaceService.getMembersAndGuests(id);
        return ResponseEntity.ok(members);
    }

    @GetMapping
    public HttpEntity<?> getMyWorkspaces(@CurrentUser User user){
        List<WorkspaceDto> workspaces = workspaceService.getMyWorkspaces(user);
        return ResponseEntity.ok(workspaces);
    }

    @PutMapping("/addOrRemovePermission")
    public HttpEntity<?> addOrRemovePermissionToRole(@RequestBody WorkspaceRoleDto workspaceRoleDto){
        ApiResponse apiResponse = workspaceService.addOrRemovePermissionToRole(workspaceRoleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }



}
