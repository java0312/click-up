package uz.pdp.clickup.service;


import uz.pdp.clickup.entity.Comment;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CommentDto;

import java.util.List;

public interface CommentService {


    ApiResponse addComment(CommentDto commentDto);

    ApiResponse editComment(Long id, CommentDto commentDto);

    ApiResponse deleteComment(Long id);

    List<Comment> getAllCommentsByTaskId(Long taskId);

}
