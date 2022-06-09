package uz.pdp.clickup.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.Status;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long> {

    boolean existsByNameAndCategoryId(String name, Long category_id);

    boolean existsAllByNameAndCategoryIdAndIdNot(String name, Long category_id, Long id);

    List<Status> findAllByCategoryId(Long category_id);
}
