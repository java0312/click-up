package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.TaskHistory;
import uz.pdp.clickup.entity.TasksTags;
import uz.pdp.clickup.exceptions.ResourceNotFoundException;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TasksTagsDto;
import uz.pdp.clickup.repository.TagRepository;
import uz.pdp.clickup.repository.TaskHistoryRepository;
import uz.pdp.clickup.repository.TaskRepository;
import uz.pdp.clickup.repository.TasksTagsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TasksTagsServiceImpl implements TasksTagsService{

    @Autowired
    TagRepository tagRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TasksTagsRepository tasksTagsRepository;

    @Autowired
    TaskHistoryRepository taskHistoryRepository;

    @Override
    public ApiResponse addTasksTags(TasksTagsDto tasksTagsDto) {
        boolean exists = tasksTagsRepository.existsByTagIdAndTaskId(tasksTagsDto.getTaskId(), tasksTagsDto.getTagId());
        if (exists)
            return new ApiResponse("TasksTags exists!", false);

        TasksTags save = tasksTagsRepository.save(new TasksTags(
                taskRepository.findById(tasksTagsDto.getTaskId()).orElseThrow(() -> new ResourceNotFoundException("Task not found!")),
                tagRepository.findById(tasksTagsDto.getTagId()).orElseThrow(() -> new ResourceNotFoundException("Tag not found!"))
        ));

        /*
         * Task history
         * */
        taskHistoryRepository.save(new TaskHistory(
                save.getTask(),
                "TaskTags",
                "",
                "create",
                "Tag added to task!"
        ));

        return new ApiResponse("TasksTags added!", true);
    }

    @Override
    public ApiResponse deleteTasksTags(Long id) {
        try {
            Optional<TasksTags> optionalTasksTags = tasksTagsRepository.findById(id);
            if (optionalTasksTags.isEmpty())
                return new ApiResponse("TaskTag not found!", false);

            tasksTagsRepository.deleteById(id);

            /*
             * Task history
             * */
            taskHistoryRepository.save(new TaskHistory(
                    optionalTasksTags.get().getTask(),
                    "TaskTags",
                    "",
                    "delete",
                    "Tag deleted from task!"
            ));
            return new ApiResponse("TasksTags deleted!", true);
        }catch (Exception e){
            return new ApiResponse("Error", false);
        }
    }

    @Override
    public List<TasksTags> getAllTasksTagsByTaskId(Long taskId) {
        return tasksTagsRepository.findAllByTaskId(taskId);
    }
}
