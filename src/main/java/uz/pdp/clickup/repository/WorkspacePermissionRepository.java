package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.WorkspacePermission;

public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission, Long> {
}
