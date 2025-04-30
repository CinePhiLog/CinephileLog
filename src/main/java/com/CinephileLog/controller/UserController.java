package com.CinephileLog.controller;

import com.CinephileLog.domain.User;
import com.CinephileLog.dto.AddUserRequest;
import com.CinephileLog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("api/user/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") long userId, @RequestBody AddUserRequest addUserRequest) {
        userService.updateUserById(userId, addUserRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("api/userByNickname/")
    public ResponseEntity<Void> getUserByNickname(@RequestParam("nickname") String nickname) {
        User user = userService.getUserByNickname(nickname);

        if (user == null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
