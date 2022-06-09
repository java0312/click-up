package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.clickup.entity.Attachment;
import uz.pdp.clickup.entity.TaskAttachment;
import uz.pdp.clickup.exceptions.ResourceNotFoundException;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TaskAttachmentDto;
import uz.pdp.clickup.repository.AttachmentRepository;
import uz.pdp.clickup.repository.TaskAttachmentRepository;
import uz.pdp.clickup.repository.TaskRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskAttachmentServiceImpl implements TaskAttachmentService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskAttachmentRepository taskAttachmentRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Override
    public ApiResponse addTaskAttachment(MultipartHttpServletRequest request, TaskAttachmentDto taskAttachmentDto) {

        Iterator<String> fileNames = request.getFileNames();
        if (fileNames.hasNext()) {
            String name = fileNames.next();
            MultipartFile file = request.getFile(name);

            if (file == null)
                return new ApiResponse("Fle not found!", false);

            String fileNameInSystem = UUID.randomUUID().toString();

            Attachment savedAttachment = attachmentRepository.save(new Attachment(
                    fileNameInSystem,
                    file.getOriginalFilename(),
                    file.getSize(),
                    file.getContentType()
            ));


            Path path = Paths.get("upload" + "/" + fileNameInSystem);
            try {
                Files.copy(file.getInputStream(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /**
             *  * * * * * * * * * * * * * *
             */
            taskAttachmentRepository.save(new TaskAttachment(
                    taskRepository.findById(taskAttachmentDto.getTaskId()).orElseThrow(() -> new ResourceNotFoundException("Task not found!")),
                    savedAttachment,
                    taskAttachmentDto.isPinCoverImage()
            ));

            return new ApiResponse("TaskAttachment saved!", true);
        }
        return new ApiResponse("TaskAttachment not saved!", false);

    }

    @Override
    public ApiResponse deleteTaskAttachment(Long id) {
        try {
            taskAttachmentRepository.deleteById(id);
            return new ApiResponse("TaskAttachment deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting!", false);
        }
    }

    @Override
    public ApiResponse pinCoverImage(Long id) {
        Optional<TaskAttachment> optionalTaskAttachment = taskAttachmentRepository.findById(id);
        if (optionalTaskAttachment.isEmpty())
            return new ApiResponse("TaskAttachment not found!", false);

        List<TaskAttachment> taskAttachmentList = taskAttachmentRepository.findAllByTaskId(optionalTaskAttachment.get().getTask().getId());
        for (TaskAttachment taskAttachment : taskAttachmentList) {
            taskAttachment.setPinCoverImage(taskAttachment.getId().equals(optionalTaskAttachment.get().getId()));
        }
        taskAttachmentRepository.saveAll(taskAttachmentList);
        return new ApiResponse("Pin cover image put", true);
    }
}
