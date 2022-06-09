package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TaskAttachmentDto;
import uz.pdp.clickup.service.TaskAttachmentService;

@RestController
@RequestMapping("/api/taskAttachment")
public class TaskAttachmentController {

    @Autowired
    TaskAttachmentService taskAttachmentService;

    @PostMapping
    public HttpEntity<?> addTaskAttachment(MultipartHttpServletRequest request,
                                           @RequestBody TaskAttachmentDto taskAttachmentDto){
        ApiResponse apiResponse = taskAttachmentService.addTaskAttachment(request, taskAttachmentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteTaskAttachment(@PathVariable Long id){
        ApiResponse apiResponse = taskAttachmentService.deleteTaskAttachment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/pinCoverImage/{id}")
    public HttpEntity<?> pinCoverImage(@PathVariable Long id){
        ApiResponse apiResponse = taskAttachmentService.pinCoverImage(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
