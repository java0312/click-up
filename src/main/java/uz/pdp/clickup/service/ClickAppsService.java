package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.ClickApps;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ClickAppsDto;

import java.util.List;

public interface ClickAppsService {
    ApiResponse addClickApps(ClickAppsDto clickAppsDto);

    ApiResponse editClickApps(Long id, ClickAppsDto clickAppsDto);

    ApiResponse deleteClickApps(Long id);

    List<ClickApps> getAllClickApps();

    ClickApps getClickApps(Long id);
}
