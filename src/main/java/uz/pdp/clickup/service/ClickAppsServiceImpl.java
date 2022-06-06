package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.ClickApps;
import uz.pdp.clickup.exceptions.ResourceNotFoundException;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ClickAppsDto;
import uz.pdp.clickup.repository.ClickAppsRepository;
import uz.pdp.clickup.repository.IconRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClickAppsServiceImpl implements ClickAppsService {

    @Autowired
    ClickAppsRepository clickAppsRepository;

    @Autowired
    IconRepository iconRepository;

    @Override
    public ApiResponse addClickApps(ClickAppsDto clickAppsDto) {

        boolean exists = clickAppsRepository.existsByName(clickAppsDto.getName());
        if (exists)
            return new ApiResponse("Click apps exists!", false);

        clickAppsRepository.save(new ClickApps(
                clickAppsDto.getName(),
                clickAppsDto.getIconId() == null ?
                        null :
                        iconRepository.findById(clickAppsDto.getIconId()).orElseThrow(() -> new ResourceNotFoundException("icon not found!"))
        ));

        return new ApiResponse("Click apps saved!", true);
    }

    @Override
    public ApiResponse editClickApps(Long id, ClickAppsDto clickAppsDto) {
        boolean exists = clickAppsRepository.existsByNameAndIdNot(clickAppsDto.getName(), id);
        if (exists)
            return new ApiResponse("This click apps exists!", false);

        Optional<ClickApps> optionalClickApps = clickAppsRepository.findById(id);
        if (optionalClickApps.isEmpty())
            return new ApiResponse("Click apps not found!", false);

        ClickApps clickApps = optionalClickApps.get();
        clickApps.setName(clickAppsDto.getName());
        clickApps.setIcon(
                clickAppsDto.getIconId() == null ?
                        null :
                        iconRepository.findById(clickAppsDto.getIconId()).orElseThrow(() -> new ResourceNotFoundException("Icon not found!"))
        );

        clickAppsRepository.save(clickApps);
        return new ApiResponse("click apps edited!", true);
    }

    @Override
    public ApiResponse deleteClickApps(Long id) {
        try {
            clickAppsRepository.deleteById(id);
            return new ApiResponse("Click apps deleted", true);
        }catch (Exception e){
            return new ApiResponse("Error in deleting", false);
        }
    }

    @Override
    public List<ClickApps> getAllClickApps() {
        return clickAppsRepository.findAll();
    }

    @Override
    public ClickApps getClickApps(Long id) {
        return clickAppsRepository.findById(id).orElse(null);
    }
}
