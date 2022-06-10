package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.TaskHistory;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {



}
