package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.*;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TagDto;
import uz.pdp.clickup.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    TasksTagsRepository tasksTagsRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskHistoryRepository taskHistoryRepository;

    @Override
    public ApiResponse addTag(TagDto tagDto) {

        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(tagDto.getWorkspaceId());
        if (optionalWorkspace.isEmpty())
            return new ApiResponse("Workspace not found!", false);

        boolean exists = tagRepository.existsByNameAndWorkspaceId(tagDto.getName(), tagDto.getWorkspaceId());
        if (exists)
            return new ApiResponse("Tag exists!", false);

        Tag savedTag = tagRepository.save(new Tag(
                tagDto.getName(),
                tagDto.getColor(),
                optionalWorkspace.get()
        ));

        boolean existsByTagIdAndTaskId = tasksTagsRepository.existsByTagIdAndTaskId(savedTag.getId(), tagDto.getTaskId());
        if (existsByTagIdAndTaskId)
            return new ApiResponse("Tag exists in this task", false);

        if (tagDto.getTaskId() != null) {
            Optional<Task> optionalTask = taskRepository.findById(tagDto.getTaskId());
            if (optionalTask.isEmpty())
                return new ApiResponse("Task not found", false);

            TasksTags savedTasksTags = tasksTagsRepository.save(new TasksTags(
                    optionalTask.get(),
                    savedTag
            ));

            /*
            * TASK HISTORY
            * */
            taskHistoryRepository.save(new TaskHistory(
                   savedTasksTags.getTask(),
                    "Tag",
                    "",
                    savedTag.getName(),
                    "tag created"
            ));
        }
        return new ApiResponse("Tag saved!", true);
    }

    @Override
    public ApiResponse editTag(Long id, TagDto tagDto) {

        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (optionalTag.isEmpty())
            return new ApiResponse("Tag not found!", false);

        boolean exists = tagRepository.existsByNameAndWorkspaceIdAndIdNot(tagDto.getName(), tagDto.getWorkspaceId(), id);
        if (exists)
            return new ApiResponse("Tag exists!", false);

        Tag tag = optionalTag.get();
        tag.setName(tagDto.getName());
        tag.setColor(tagDto.getColor());
        tagRepository.save(tag);

        return new ApiResponse("Tag edited!", false);
    }

    @Override
    public ApiResponse deleteTag(Long id) {
        try {
            tasksTagsRepository.deleteAllByTagId(id);
            tagRepository.deleteById(id);
            return new ApiResponse("Tag deleted!", true);
        }catch (Exception e){
            return new ApiResponse("Error!", false);
        }
    }

    @Override
    public List<Tag> getAllTagsByWorkspaceId(Long workspaceId) {
        return tagRepository.findAllByWorkspaceId(workspaceId);
    }
}
