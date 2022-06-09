package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByNameAndWorkspaceId(String name, Long workspace_id);

    boolean existsByNameAndWorkspaceIdAndIdNot(String name, Long workspace_id, Long id);

    List<Tag> findAllByWorkspaceId(Long workspace_id);

}
