package com.example.security.controller;

import com.example.security.model.Message;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class HelloController {
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    Message helloUser() {
        System.out.println("in helloUser");
        return new Message("hello user");
    }

    @GetMapping("/admin")
    Message helloAdmin() {
        return new Message("hello admin");
    }
}
