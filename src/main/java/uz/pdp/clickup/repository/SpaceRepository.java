package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.Space;

import java.util.List;
import java.util.UUID;

public interface SpaceRepository extends JpaRepository<Space, Long> {

    boolean existsByNameAndWorkspaceId(String name, Long workspace_id);

    List<Space> findAllByOwnerId(UUID owner_id);

    boolean existsByIdAndOwnerId(Long id, UUID owner_id);
}
