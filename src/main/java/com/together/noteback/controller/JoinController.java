package com.together.noteback.controller;

import com.together.noteback.dto.JoinDTO;
import com.together.noteback.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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

        return "ok";
    }


}
