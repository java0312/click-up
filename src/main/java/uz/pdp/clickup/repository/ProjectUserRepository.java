package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.ProjectUser;

import java.util.List;
import java.util.UUID;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long> {

    boolean existsByProjectIdAndUserId(Long project_id, UUID user_id);

    boolean existsByProjectIdAndUserIdAndIdNot(Long project_id, UUID user_id, Long id);

    List<ProjectUser> findAllByProjectId(Long project_id);
}
