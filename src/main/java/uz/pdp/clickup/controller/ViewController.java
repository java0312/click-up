package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.SpaceView;
import uz.pdp.clickup.entity.View;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ViewDto;
import uz.pdp.clickup.service.ViewService;

import java.util.List;

@RestController
@RequestMapping("/api/View")
public class ViewController {
    
    @Autowired
    ViewService viewService;

    @PostMapping
    public HttpEntity<?> addView(@RequestBody ViewDto ViewDto){
        ApiResponse apiResponse = viewService.addView(ViewDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editView(@PathVariable Long id,
                                  @RequestBody ViewDto ViewDto){
        ApiResponse apiResponse = viewService.editView(id, ViewDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteView(@PathVariable Long id){
        ApiResponse apiResponse = viewService.deleteView(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAllViews(){
        List<View> views = viewService.getAllViews();
        return ResponseEntity.ok(views);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getView(@PathVariable Long id){
        View view = viewService.getView(id);
        return ResponseEntity.ok(view);
    }

    /*
    * Space bo'yicha viewlarni olish
    * */
    @GetMapping("/bySpaceId/{spaceId}")
    public HttpEntity<?> getAllViewsBySpaceId(@PathVariable Long spaceId){
        List<View> views = viewService.getAllViewsBySpaceId(spaceId);
        return ResponseEntity.ok(views);
    }
    
}
