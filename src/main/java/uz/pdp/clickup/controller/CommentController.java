package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.Comment;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CommentDto;
import uz.pdp.clickup.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping
    public HttpEntity<?> addComment(@RequestBody CommentDto CommentDto){
        ApiResponse apiResponse = commentService.addComment(CommentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editComment(@PathVariable Long id,
                                      @RequestBody CommentDto CommentDto){
        ApiResponse apiResponse = commentService.editComment(id, CommentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteComment(@PathVariable Long id){
        ApiResponse apiResponse = commentService.deleteComment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/byTaskId/{taskId}")
    public HttpEntity<?> getAllCategoriesByTaskId(@PathVariable Long taskId){
        List<Comment> CommentList = commentService.getAllCommentsByTaskId(taskId);
        return ResponseEntity.ok(CommentList);
    }

}
