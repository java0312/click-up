package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.ClickApps;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ClickAppsDto;
import uz.pdp.clickup.service.ClickAppsService;

import java.util.List;

@RestController
@RequestMapping("/api/clickApps")
public class ClickAppsController {
    
    @Autowired
    ClickAppsService clickAppsService;

    @PostMapping
    public HttpEntity<?> addClickApps(@RequestBody ClickAppsDto clickAppsDto){
        ApiResponse apiResponse = clickAppsService.addClickApps(clickAppsDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editClickApps(@PathVariable Long id,
                                  @RequestBody ClickAppsDto clickAppsDto){
        ApiResponse apiResponse = clickAppsService.editClickApps(id, clickAppsDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteClickApps(@PathVariable Long id){
        ApiResponse apiResponse = clickAppsService.deleteClickApps(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAllClickAppss(){
        List<ClickApps> clickApps = clickAppsService.getAllClickApps();
        return ResponseEntity.ok(clickApps);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getClickApps(@PathVariable Long id){
        ClickApps clickApps = clickAppsService.getClickApps(id);
        return ResponseEntity.ok(clickApps);
    }
    
}
