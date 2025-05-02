package com.CinephileLog.controller;

import com.CinephileLog.domain.User;
import com.CinephileLog.dto.AddUserRequest;
import com.CinephileLog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("api/user/{id}")        // 업데이트
    public ResponseEntity<Void> updateUser(@PathVariable("id") long userId, @RequestBody AddUserRequest addUserRequest) {
        userService.updateUserById(userId, addUserRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("api/userByNickname/")      // 닉네임에 따른 조회
    public ResponseEntity<Void> getUserByNickname(@RequestParam("nickname") String nickname) {
        Optional<User> user = userService.getUserByNickname(nickname);

        if (user.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build(); // 유저가 없으면 404 (Not Found) 반환
        }
    }

}
