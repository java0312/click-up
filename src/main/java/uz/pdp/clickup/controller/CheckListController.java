package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.CheckList;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CheckListDto;
import uz.pdp.clickup.service.CheckListService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/checkList")
public class CheckListController {

    @Autowired
    CheckListService checkListService;

    @PostMapping
    public HttpEntity<?> addCheckList(@RequestBody CheckListDto checkListDto){
        ApiResponse apiResponse = checkListService.addCheckList(checkListDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editCheckList(@PathVariable Long id,
                                       @RequestBody CheckListDto checkListDto){
        ApiResponse apiResponse = checkListService.editCheckList(id, checkListDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCheckList(@PathVariable Long id){
        ApiResponse apiResponse = checkListService.deleteCheckList(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/byTaskId/{taskId}")
    public HttpEntity<?> getAllCheckListByTaskId(@PathVariable Long taskId){
        List<CheckList> checkLists = checkListService.getAllCheckListByTaskId(taskId);
        return ResponseEntity.ok(checkLists);
    }


    /**
     * CheckList ga user tayinlanganda check list ichidagi barcha itemlarga shu user tayinlanadi
     *
     */
    @PutMapping("/assignUserToCheckList")
    public HttpEntity<?> assignUserToCheckList(@RequestParam Long id, @RequestParam UUID userId){
        ApiResponse apiResponse = checkListService.assignUserToCheckList(id, userId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * CheckList ning itemlari ga tayinlangan barcha userlarni olib lashlash
     */
    @DeleteMapping("/unAssignAllByCheckListId/{id}")
    public HttpEntity<?> unAssignAllByCheckListId(@PathVariable Long id){
        ApiResponse apiResponse = checkListService.unAssignAllByCheckListId(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * Check List item larining barchasini  resolve = true  qilish
     */
    @PutMapping("/checkAllItemByCheckListId/{id}")
    public HttpEntity<?> checkAllItemByCheckListId(@PathVariable Long id){
        ApiResponse apiResponse = checkListService.checkAllItemByCheckListId(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * Check List item larining barchasini  resolve = false  qilish
     */
    @PutMapping("/unCheckAllItemByCheckListId/{id}")
    public HttpEntity<?> unCheckAllItemByCheckListId(@PathVariable Long id){
        ApiResponse apiResponse = checkListService.unCheckAllItemByCheckListId(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


}
