package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.TasksUsers;

import java.util.List;
import java.util.UUID;

public interface TasksUsersRepository extends JpaRepository<TasksUsers, Long> {

    boolean existsByTaskIdAndUserId(Long task_id, UUID user_id);

    List<TasksUsers> findAllByTaskId(Long task_id);
}
