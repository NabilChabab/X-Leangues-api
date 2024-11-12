package com.example.x_leagues.web.api.v1.hunt;


import com.example.x_leagues.model.Hunt;
import com.example.x_leagues.services.dto.HuntDTO;
import com.example.x_leagues.services.impl.HuntServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/api/v1/hunt")
@RestController
public class HuntController {


    private final HuntServiceImpl huntService;

    public HuntController(HuntServiceImpl huntService) {
        this.huntService = huntService;
    }
    @PostMapping("/save")
    public ResponseEntity<Map<String,String>> registerResult(
            @Valid @RequestBody HuntDTO huntRequest) {

        double score = huntService.registerHunt(huntRequest);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hunt registered successfully , and the score is "+ score );
        return ResponseEntity.ok(response);
    }
}
