package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.CheckList;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CheckListDto;

import java.util.List;
import java.util.UUID;

public interface CheckListService {
    ApiResponse addCheckList(CheckListDto checkListDto);

    ApiResponse editCheckList(Long id, CheckListDto checkListDto);

    ApiResponse deleteCheckList(Long id);

    List<CheckList> getAllCheckListByTaskId(Long taskId);

    ApiResponse assignUserToCheckList(Long id, UUID userId);

    ApiResponse unAssignAllByCheckListId(Long id);

    ApiResponse checkAllItemByCheckListId(Long id);

    ApiResponse unCheckAllItemByCheckListId(Long id);
}
