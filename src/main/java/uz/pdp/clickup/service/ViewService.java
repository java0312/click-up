package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.SpaceView;
import uz.pdp.clickup.entity.View;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ViewDto;

import java.util.List;

public interface ViewService {
    ApiResponse addView(ViewDto viewDto);

    ApiResponse editView(Long id, ViewDto viewDto);

    ApiResponse deleteView(Long id);

    List<View> getAllViews();

    View getView(Long id);

    List<View> getAllViewsBySpaceId(Long spaceId);

}
