package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.CheckListItem;

import java.util.List;

public interface CheckListItemRepository extends JpaRepository<CheckListItem, Long> {

    List<CheckListItem> findAllByCheckListId(Long checkList_id);

    boolean existsByNameAndCheckListId(String name, Long checkList_id);

    boolean existsByNameAndCheckListIdAndIdNot(String name, Long checkList_id, Long id);

    void deleteAllByCheckListId(Long checkList_id);

}
