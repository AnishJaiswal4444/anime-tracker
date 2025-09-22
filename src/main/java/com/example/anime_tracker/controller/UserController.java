package com.example.anime_tracker.controller;

import com.example.anime_tracker.dto.UserDTO;
import com.example.anime_tracker.dto.UserRequest;
import com.example.anime_tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserDTO register(@Valid @RequestBody UserRequest request) {
        return userService.createUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );
    }

}
