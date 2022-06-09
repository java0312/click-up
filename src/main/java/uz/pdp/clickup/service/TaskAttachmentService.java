package uz.pdp.clickup.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TaskAttachmentDto;

public interface TaskAttachmentService {
    ApiResponse addTaskAttachment(MultipartHttpServletRequest request, TaskAttachmentDto taskAttachmentDto);

    ApiResponse deleteTaskAttachment(Long id);

    ApiResponse pinCoverImage(Long id);

}
