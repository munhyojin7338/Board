package com.example.board.springswagger.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/api")
public class SwController {
    @GetMapping("/hello")
    public String Hello(){
        return "hello";
    }
}
