package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.CheckList;

import java.util.List;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

    List<CheckList> findAllByTaskId(Long task_id);
}
