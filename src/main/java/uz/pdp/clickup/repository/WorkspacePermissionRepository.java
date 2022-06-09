package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.WorkspacePermission;
import uz.pdp.clickup.entity.WorkspaceRole;
import uz.pdp.clickup.entity.enums.WorkspacePermissionName;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission, Long> {

    List<WorkspacePermission> findAllByWorkspaceRole(WorkspaceRole workspaceRole);

    Optional<WorkspacePermission> findByWorkspaceRoleIdAndPermission(UUID workspaceRole_id, WorkspacePermissionName permission);

}
