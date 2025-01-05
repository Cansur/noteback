package com.together.noteback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.together.noteback.service.BoardService;


@RestController
public class TestController {

    @Autowired
    private BoardService boardService;    

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/api/hello")
    public String test() {
        return "Hello, world!";
    }

    @GetMapping("/create")
    public String createe() {
        boardService.writeBoard();
        return "new String()";
    }
    
}
