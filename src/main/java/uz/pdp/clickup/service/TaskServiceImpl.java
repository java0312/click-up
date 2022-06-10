package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.*;
import uz.pdp.clickup.exceptions.ResourceNotFoundException;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TaskDto;
import uz.pdp.clickup.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PriorityRepository priorityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TasksUsersRepository tasksUsersRepository;

    @Autowired
    TaskHistoryRepository taskHistoryRepository;

    @Override
    public ApiResponse addTask(TaskDto taskDto) {

        boolean exists = taskRepository.existsByNameAndStatusId(taskDto.getName(), taskDto.getCategoryId());
        if (exists)
            return new ApiResponse("Task exists!", false);


        Task savedTask = taskRepository.save(new Task(
                taskDto.getName(),
                statusRepository.findById(taskDto.getStatusId()).orElseThrow(() -> new ResourceNotFoundException("Status not found!")),
                taskDto.getDescription(),
                taskDto.getCategoryId() == null ?
                        null :
                        categoryRepository.findById(taskDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found!")),
                taskDto.getPriorityId() == null ?
                        null :
                        priorityRepository.findById(taskDto.getPriorityId()).orElse(null),
                taskDto.getParentTaskId() == null ?
                        null :
                        taskRepository.findById(taskDto.getParentTaskId()).orElse(null),
                taskDto.getStartedDate(),
                taskDto.getStartedTimeHas(),
                taskDto.getDueDate(),
                taskDto.getDueTimeHas(),
                taskDto.getEstimateTime(),
                null
        ));

        /*
         * Task history
         * */
        taskHistoryRepository.save(new TaskHistory(
                savedTask,
                "task",
                "",
                "create",
                "Task created!"
        ));


        List<TasksUsers> tasksUsersList = new ArrayList<>();
        for (UUID uuid : taskDto.getUsersIdList()) {
            tasksUsersList.add(new TasksUsers(
                    savedTask,
                    userRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("User not found!"))
            ));
        }
        tasksUsersRepository.saveAll(tasksUsersList);

        /*
         * Task history
         * */
        taskHistoryRepository.save(new TaskHistory(
                savedTask,
                "TaskUser",
                "",
                "create",
                "TaskUsers added!"
        ));

        return new ApiResponse("Task saved!", true);
    }

    @Override
    public ApiResponse editTask(Long id, TaskDto taskDto) {

        boolean exists = taskRepository.existsByNameAndStatusIdAndIdNot(taskDto.getName(), taskDto.getStatusId(), id);
        if (exists)
            return new ApiResponse("Task exists!", false);

        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty())
            return new ApiResponse("Task not found!", false);

        Task task = optionalTask.get();
        task.setName(taskDto.getName());
        task.setStatus(statusRepository.findById(taskDto.getStatusId()).orElseThrow(() -> new ResourceNotFoundException("Status not found!")));
        task.setDescription(taskDto.getDescription());
        task.setPriority(taskDto.getPriorityId() == null ?
                null :
                priorityRepository.findById(taskDto.getPriorityId()).orElse(null));
        task.setParentTask(taskDto.getParentTaskId() == null ?
                null :
                taskRepository.findById(taskDto.getParentTaskId()).orElse(null));

        task.setStartedDate(taskDto.getStartedDate());
        task.setStartedTimeHas(taskDto.getStartedTimeHas());
        task.setDueDate(taskDto.getDueDate());
        task.setDueTimeHas(taskDto.getDueTimeHas());
        task.setEstimateTime(taskDto.getEstimateTime());

        /*
         * Task history
         * */
        taskHistoryRepository.save(new TaskHistory(
                task,
                "task",
                optionalTask.get().toString(),
                task.toString(),
                "Task edited!"
        ));

        taskRepository.save(task);

        return new ApiResponse("Task edited!", true);
    }

    @Override
    public ApiResponse deleteTask(Long id) {
        try {
            Optional<Task> optionalTask = taskRepository.findById(id);
            if (optionalTask.isEmpty())
                return new ApiResponse("Task not found!", false);

            taskRepository.deleteById(id);

            /*
             * Task history
             * */
            taskHistoryRepository.save(new TaskHistory(
                    optionalTask.get(),
                    "task",
                    "add",
                    "delete",
                    "Task deleted!"
            ));


            return new ApiResponse("Task deleted!", true);
        }catch (Exception e){
            return new ApiResponse("Error in deleting!",false);
        }
    }

    @Override
    public List<Task> getAllTasksByCategoryId(Long categoryId) {
        return taskRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public List<Task> getAllTasksByStatusId(Long statusId) {
        return taskRepository.findAllByStatusId(statusId);
    }

    @Override
    public ApiResponse editStatusOfTask(Long taskId, Long statusId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty())
            return new ApiResponse("Task not found!", false);

        Optional<Status> optionalStatus = statusRepository.findById(statusId);
        if (optionalStatus.isEmpty())
            return new ApiResponse("Status not found!", false);

        if (!optionalTask.get().getCategory().equals(optionalStatus.get().getCategory()))
            return new ApiResponse("Not possible", false);

        Task task = optionalTask.get();
        task.setStatus(optionalStatus.get());
        taskRepository.save(task);

        return new ApiResponse("Task status edited!", false);
    }


}










