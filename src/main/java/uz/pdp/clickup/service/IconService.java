package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.Icon;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.IconDto;

import java.util.List;

public interface IconService {

    ApiResponse addIcon(IconDto iconDto);

    ApiResponse editIcon(Long id, IconDto iconDto);

    ApiResponse deleteIcon(Long id);

    List<Icon> getAllIcons();

    Icon getIcon(Long id);
}
