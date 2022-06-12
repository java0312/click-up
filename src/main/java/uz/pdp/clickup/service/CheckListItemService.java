package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.CheckListItem;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CheckListItemDto;

import java.util.List;

public interface CheckListItemService {
    ApiResponse addCheckListItem(CheckListItemDto checkListItemDto);

    ApiResponse editCheckListItem(Long id, CheckListItemDto checkListItemDto);

    ApiResponse deleteCheckListItem(Long id);

    List<CheckListItem> getAllCheckListItemByCheckListId(Long checkListId);

}
