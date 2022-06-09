package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.CategoryUser;

import java.util.List;
import java.util.UUID;

public interface CategoryUserRepository extends JpaRepository<CategoryUser, Long> {

    void deleteAllByCategoryId(Long category_id);

    boolean existsByCategoryIdAndUserId(Long category_id, UUID user_id);

    List<CategoryUser> findAllByCategoryId(Long category_id);
}
