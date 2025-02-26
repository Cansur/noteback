package com.together.noteback.controller;

import com.together.noteback.dto.JoinDTO;
import com.together.noteback.service.JoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PostMapping("/api/join")
    public String joinProcess(@RequestBody JoinDTO joinDTO){

        joinService.joinProcess(joinDTO);

        return "회원가입 성공";
    }

    @GetMapping("/api/check-username")
    public ResponseEntity<Map<String, Boolean>> checkUsername(@RequestParam String username) {

        boolean isAvailable = joinService.isUsernameAvailable(username);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isAvailable);

        return ResponseEntity.ok(response);
    }


}
