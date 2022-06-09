package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.TasksTags;

import java.util.List;

public interface TasksTagsRepository extends JpaRepository<TasksTags, Long> {

    boolean existsByTagIdAndTaskId(Long tag_id, Long task_id);

    void deleteAllByTagId(Long tag_id);

    List<TasksTags> findAllByTaskId(Long task_id);

}
