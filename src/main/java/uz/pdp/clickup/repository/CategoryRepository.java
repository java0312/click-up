package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.Category;
import uz.pdp.clickup.entity.Project;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByNameAndProjectId(String name, Long project_id);

    boolean existsByNameAndProjectIdAndIdNot(String name, Long project_id, Long id);

    List<Category> findAllByProjectId(Long project_id);
}
