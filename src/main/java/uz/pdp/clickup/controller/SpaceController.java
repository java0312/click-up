package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.codec.ResourceEncoder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.Space;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.SpaceDto;
import uz.pdp.clickup.security.CurrentUser;
import uz.pdp.clickup.service.SpaceService;

import java.util.List;

@RestController
@RequestMapping("/api/space")
public class SpaceController {

    @Autowired
    SpaceService spaceService;

    @GetMapping
    public HttpEntity<?> getAllSpaces(@CurrentUser User owner){
        List<Space> spaces = spaceService.getAllSpaces(owner);
        return ResponseEntity.ok(spaces);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getSpace(@PathVariable Long id, @CurrentUser User user){
        Space space = spaceService.getSpace(id, user);
        return ResponseEntity.status(space != null ? 200 : 409).body(space);
    }

    @PostMapping
    public HttpEntity<?> addSpace(@RequestBody SpaceDto spaceDto, @CurrentUser User owner){
        ApiResponse apiResponse = spaceService.addSpace(spaceDto, owner);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editSpace(@PathVariable Long id,
                                  @RequestBody SpaceDto spaceDto){
        ApiResponse apiResponse = spaceService.editSpace(id, spaceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteSpace(@PathVariable Long id){
        ApiResponse apiResponse = spaceService.deleteSpace(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
