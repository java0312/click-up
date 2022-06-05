package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.Workspace;

import java.util.List;
import java.util.UUID;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    boolean existsByNameAndOwnerId(String name, UUID owner_id);

    boolean existsByNameAndOwnerIdAndIdNot(String name, UUID owner_id, Long id);

    List<Workspace> findAllByOwnerId(UUID owner_id);
}
