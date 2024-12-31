package com.together.noteback.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @GetMapping("/")
    public String getMethodName() {
        return "index";
    }

    @GetMapping("/api/hello")
    public String test() {
        return "Hello, world!";
    }
}
