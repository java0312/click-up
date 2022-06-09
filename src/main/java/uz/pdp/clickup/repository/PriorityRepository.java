package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.Priority;

public interface PriorityRepository extends JpaRepository<Priority, Long> {
}
