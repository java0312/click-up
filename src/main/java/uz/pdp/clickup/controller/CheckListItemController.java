package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.CheckListItem;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CheckListItemDto;
import uz.pdp.clickup.service.CheckListItemService;

import java.util.List;

@RestController
@RequestMapping("/api/checkListItem")
public class CheckListItemController {

    @Autowired
    CheckListItemService checkListItemService;

    @PostMapping
    public HttpEntity<?> addCheckListItem(@RequestBody CheckListItemDto checkListItemDto){
        ApiResponse apiResponse = checkListItemService.addCheckListItem(checkListItemDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editCheckListItem(@PathVariable Long id,
                                          @RequestBody CheckListItemDto checkListItemDto){
        ApiResponse apiResponse = checkListItemService.editCheckListItem(id, checkListItemDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCheckListItem(@PathVariable Long id){
        ApiResponse apiResponse = checkListItemService.deleteCheckListItem(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/byCheckListId/{checkListId}")
    public HttpEntity<?> getAllCheckListItemByCheckListId(@PathVariable Long checkListId){
        List<CheckListItem> checkListItems = checkListItemService.getAllCheckListItemByCheckListId(checkListId);
        return ResponseEntity.ok(checkListItems);
    }

}
