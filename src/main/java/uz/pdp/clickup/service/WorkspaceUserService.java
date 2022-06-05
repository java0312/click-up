package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.entity.WorkspaceUser;
import uz.pdp.clickup.repository.WorkspaceUserRepository;

import java.util.List;

public interface WorkspaceUserService {

    List<WorkspaceUser> getMembersAndGuestsByWorkspaceId(Long workspaceId);

}
