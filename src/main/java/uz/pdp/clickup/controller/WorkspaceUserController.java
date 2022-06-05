package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.entity.WorkspaceUser;
import uz.pdp.clickup.service.WorkspaceUserService;

import java.util.List;

@RestController
@RequestMapping("/api/workspaceUser")
public class WorkspaceUserController {

    @Autowired
    WorkspaceUserService workspaceUserService;

    @GetMapping("/membersAndGuestsByWorkspaceId/{workspaceId}")
    public HttpEntity<?> getMembersAndGuestsByWorkspaceId(@PathVariable Long workspaceId){
        List<WorkspaceUser> userList = workspaceUserService.getMembersAndGuestsByWorkspaceId(workspaceId);
        return ResponseEntity.ok(userList);
    }

}
