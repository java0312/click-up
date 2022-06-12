package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.SpaceView;

import java.util.List;

public interface SpaceViewRepository extends JpaRepository<SpaceView, Long> {

    List<SpaceView> findAllBySpaceId(Long space_id);
}
