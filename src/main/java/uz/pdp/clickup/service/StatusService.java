package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.Status;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.StatusDto;

import java.util.List;

public interface StatusService {
    ApiResponse addStatus(StatusDto statusDto);

    ApiResponse editStatus(Long id, StatusDto statusDto);

    ApiResponse deleteStatus(Long id);

    List<Status> getAllStatusByCategoryId(Long categoryId);

}
