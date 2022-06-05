package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.WorkspaceRole;

import java.util.Optional;
import java.util.UUID;

public interface WorkspaceRoleRepository extends JpaRepository<WorkspaceRole, UUID> {

    WorkspaceRole findByName(String name);

    boolean existsByWorkspaceIdAndName(Long workspace_id, String name);

}
