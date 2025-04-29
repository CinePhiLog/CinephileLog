package com.CinephileLog.controller;

import com.CinephileLog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("api/user/{id}")
    public ResponseEntity<Void> terminateUser(@PathVariable("id") long userId) {
        userService.terminateUserById(userId);
        return ResponseEntity.ok().build();
    }
}
