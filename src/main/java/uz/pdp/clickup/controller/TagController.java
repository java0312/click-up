package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.Tag;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TagDto;
import uz.pdp.clickup.service.TagService;
import uz.pdp.clickup.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @PostMapping
    public HttpEntity<?> addTag(@RequestBody TagDto tagDto){
        ApiResponse apiResponse = tagService.addTag(tagDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editTag(@PathVariable Long id,
                                      @RequestBody TagDto tagDto){
        ApiResponse apiResponse = tagService.editTag(id, tagDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteTag(@PathVariable Long id){
        ApiResponse apiResponse = tagService.deleteTag(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/byWorkspaceId/{workspaceId}")
    public HttpEntity<?> getAllCategoriesByTaskId(@PathVariable Long workspaceId){
        List<Tag> TagList = tagService.getAllTagsByWorkspaceId(workspaceId);
        return ResponseEntity.ok(TagList);
    }

}
