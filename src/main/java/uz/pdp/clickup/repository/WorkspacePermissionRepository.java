package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.WorkspacePermission;
import uz.pdp.clickup.entity.WorkspaceRole;

import java.util.List;

public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission, Long> {

    List<WorkspacePermission> findAllByWorkspaceRole(WorkspaceRole workspaceRole);

}
