package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.TaskAttachment;

import java.util.List;

public interface TaskAttachmentRepository extends JpaRepository<TaskAttachment, Long> {

    List<TaskAttachment> findAllByTaskId(Long task_id);
}
