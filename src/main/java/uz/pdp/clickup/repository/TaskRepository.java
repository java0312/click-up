package uz.pdp.clickup.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickup.entity.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    boolean existsByNameAndStatusId(String name, Long status_id);

    boolean existsByNameAndStatusIdAndIdNot(String name, Long status_id, Long id);

    List<Task> findAllByCategoryId(Long category_id);

    List<Task> findAllByStatusId(Long status_id);
}
