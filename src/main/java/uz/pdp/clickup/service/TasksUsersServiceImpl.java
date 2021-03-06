package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.Task;
import uz.pdp.clickup.entity.TaskHistory;
import uz.pdp.clickup.entity.TasksUsers;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TasksUsersDto;
import uz.pdp.clickup.repository.TaskHistoryRepository;
import uz.pdp.clickup.repository.TaskRepository;
import uz.pdp.clickup.repository.TasksUsersRepository;
import uz.pdp.clickup.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TasksUsersServiceImpl implements TasksUsersService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TasksUsersRepository tasksUsersRepository;

    @Autowired
    TaskHistoryRepository taskHistoryRepository;

    @Override
    public ApiResponse addTasksUsers(TasksUsersDto tasksUsersDto) {

        List<UUID> usersId = tasksUsersDto.getUsersId();
        List<TasksUsers> tasksUsersList = new ArrayList<>();
        for (UUID id : usersId) {
            boolean exists = tasksUsersRepository.existsByTaskIdAndUserId(tasksUsersDto.getTaskId(), id);
            if (!exists){

                Optional<User> optionalUser = userRepository.findById(id);
                if (optionalUser.isEmpty())
                    return new ApiResponse("User not found!", false);

                Optional<Task> optionalTask = taskRepository.findById(tasksUsersDto.getTaskId());
                if (optionalTask.isEmpty())
                    return new ApiResponse("Task not found!", false);

                tasksUsersList.add(new TasksUsers(
                        optionalTask.get(),
                        optionalUser.get()
                ));



                /*
                 * Task history
                 * */
                taskHistoryRepository.save(new TaskHistory(
                        optionalTask.get(),
                        "TasUsers",
                        "",
                        "add",
                        "Users added to task"
                ));
            }
        }

        tasksUsersRepository.saveAll(tasksUsersList);



        return new ApiResponse("TasksUsers saved!", true);
    }

    @Override
    public ApiResponse deleteTasksUsers(Long id) {
        try {

            Optional<TasksUsers> optionalTasksUsers = tasksUsersRepository.findById(id);
            if (optionalTasksUsers.isEmpty())
                return new ApiResponse("TaskUsers not found!", false);

            tasksUsersRepository.deleteById(id);

            /*
             * Task history
             * */
            taskHistoryRepository.save(new TaskHistory(
                    optionalTasksUsers.get().getTask(),
                    "TasUsers",
                    "add",
                    "remove",
                    "User deleted from task"
            ));
            return new ApiResponse("TasksUsers deleted!", true);
        }catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    @Override
    public List<TasksUsers> getAllTasksUsersByTaskId(Long taskId) {
        return tasksUsersRepository.findAllByTaskId(taskId);
    }
}
