package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.WorkspaceUser;
import uz.pdp.clickup.repository.WorkspaceUserRepository;

import java.util.List;

@Service
public class WorkspaceUserServiceImpl implements WorkspaceUserService{

    @Autowired
    WorkspaceUserRepository workspaceUserRepository;

    @Override
    public List<WorkspaceUser> getMembersAndGuestsByWorkspaceId(Long workspaceId) {
        List<WorkspaceUser> members = workspaceUserRepository.findAllByWorkspaceIdAndWorkspaceRole_Name(workspaceId, "ROLE_MEMBER");
        List<WorkspaceUser> guests = workspaceUserRepository.findAllByWorkspaceIdAndWorkspaceRole_Name(workspaceId, "ROLE_GUEST");
        members.addAll(guests);
        return members;
    }

}
