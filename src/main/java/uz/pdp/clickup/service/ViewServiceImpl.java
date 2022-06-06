package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.View;
import uz.pdp.clickup.exceptions.ResourceNotFoundException;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ViewDto;
import uz.pdp.clickup.repository.ViewRepository;
import uz.pdp.clickup.repository.IconRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ViewServiceImpl implements ViewService {

    @Autowired
    ViewRepository viewRepository;

    @Autowired
    IconRepository iconRepository;

    @Override
    public ApiResponse addView(ViewDto ViewDto) {

        boolean exists = viewRepository.existsByName(ViewDto.getName());
        if (exists)
            return new ApiResponse("Click apps exists!", false);

        viewRepository.save(new View(
                ViewDto.getName(),
                ViewDto.getIconId() == null ?
                        null :
                        iconRepository.findById(ViewDto.getIconId()).orElseThrow(() -> new ResourceNotFoundException("icon not found!"))
        ));

        return new ApiResponse("Click apps saved!", true);
    }

    @Override
    public ApiResponse editView(Long id, ViewDto ViewDto) {
        boolean exists = viewRepository.existsByNameAndIdNot(ViewDto.getName(), id);
        if (exists)
            return new ApiResponse("This click apps exists!", false);

        Optional<View> optionalView = viewRepository.findById(id);
        if (optionalView.isEmpty())
            return new ApiResponse("Click apps not found!", false);

        View view = optionalView.get();
        view.setName(ViewDto.getName());
        view.setIcon(
                ViewDto.getIconId() == null ?
                        null :
                        iconRepository.findById(ViewDto.getIconId()).orElseThrow(() -> new ResourceNotFoundException("Icon not found!"))
        );

        viewRepository.save(view);
        return new ApiResponse("click apps edited!", true);
    }

    @Override
    public ApiResponse deleteView(Long id) {
        try {
            viewRepository.deleteById(id);
            return new ApiResponse("Click apps deleted", true);
        }catch (Exception e){
            return new ApiResponse("Error in deleting", false);
        }
    }

    @Override
    public List<View> getAllViews() {
        return viewRepository.findAll();
    }

    @Override
    public View getView(Long id) {
        return viewRepository.findById(id).orElse(null);
    }
}
