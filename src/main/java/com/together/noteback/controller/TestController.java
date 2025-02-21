package com.together.noteback.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.together.noteback.service.BoardService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/test")
public class TestController {

    private final BoardService boardService;

    public TestController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/success")
    public String testmethod() {
        return "scucess";
    }

    @PostMapping("/")
    public void postMethodName(@RequestBody Map<String, String> request) {
        //TODO: process POST request
        System.out.println(request.get("username"));
    }
}
