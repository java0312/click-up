package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.Tag;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.TagDto;

import java.util.List;

public interface TagService {
    ApiResponse addTag(TagDto tagDto);

    ApiResponse editTag(Long id, TagDto tagDto);

    ApiResponse deleteTag(Long id);

    List<Tag> getAllTagsByWorkspaceId(Long workspaceId);
}
