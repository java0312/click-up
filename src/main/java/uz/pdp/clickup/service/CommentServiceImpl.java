package uz.pdp.clickup.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.Comment;
import uz.pdp.clickup.entity.Task;
import uz.pdp.clickup.entity.TasksUsers;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CommentDto;
import uz.pdp.clickup.repository.CommentRepository;
import uz.pdp.clickup.repository.TaskRepository;
import uz.pdp.clickup.repository.TasksUsersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TasksUsersRepository tasksUsersRepository;

    @Override
    public ApiResponse addComment(CommentDto commentDto) {
        Optional<Task> optionalTask = taskRepository.findById(commentDto.getTaskId());
        if (optionalTask.isEmpty())
            return new ApiResponse("Task not found!", false);

        Optional<TasksUsers> optionalTasksUsers = tasksUsersRepository.findById(commentDto.getTaskUserId());
        if (optionalTasksUsers.isEmpty())
            return new ApiResponse("TaskUser not found!", false);

        if (!optionalTask.get().getId().equals(optionalTasksUsers.get().getTask().getId()))
            return new ApiResponse("Not possible", false);

        commentRepository.save(new Comment(
                optionalTasksUsers.get(),
                optionalTask.get(),
                commentDto.getText()
        ));

        return new ApiResponse("Comment saved!", true);
    }

    @Override
    public ApiResponse editComment(Long id, CommentDto commentDto) {

        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isEmpty())
            return new ApiResponse("Comment not found!", false);

        Optional<Task> optionalTask = taskRepository.findById(commentDto.getTaskId());
        if (optionalTask.isEmpty())
            return new ApiResponse("Task not found!", false);

        Optional<TasksUsers> optionalTasksUsers = tasksUsersRepository.findById(commentDto.getTaskUserId());
        if (optionalTasksUsers.isEmpty())
            return new ApiResponse("TaskUser not found!", false);

        if (!optionalTask.get().getId().equals(optionalTasksUsers.get().getTask().getId()))
            return new ApiResponse("Not possible", false);

        Comment comment = optionalComment.get();
        comment.setTask(optionalTask.get());
        comment.setText(commentDto.getText());
        comment.setTasksUsers(optionalTasksUsers.get());

        commentRepository.save(comment);

        return new ApiResponse("Comment edited!", true);
    }

    @Override
    public ApiResponse deleteComment(Long id) {
        try {
            commentRepository.deleteById(id);
            return new ApiResponse("Comment deleted!", false);
        }catch (Exception e){
            return new ApiResponse("Error in deleting!", false);
        }
    }

    @Override
    public List<Comment> getAllCommentsByTaskId(Long taskId) {
        return commentRepository.findAllByTaskId(taskId);
    }
}
