package com.together.noteback.controller;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.together.noteback.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private BoardService boardService;    

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
    
    
    @GetMapping("/username")
    public String test() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        return "Username : " + username;
    }
    
}
