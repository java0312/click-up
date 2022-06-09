package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.Task;
import uz.pdp.clickup.entity.TasksUsers;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TasksUsersDto;
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
            }
        }

        tasksUsersRepository.saveAll(tasksUsersList);

        return new ApiResponse("TasksUsers saved!", true);
    }

    @Override
    public ApiResponse deleteTasksUsers(Long id) {
        try {
            tasksUsersRepository.deleteById(id);
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
