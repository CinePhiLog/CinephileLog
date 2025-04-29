package com.CinephileLog.controller;

import com.CinephileLog.domain.user.Grade;
import com.CinephileLog.domain.user.User;
import com.CinephileLog.repository.GradeRepository;
import com.CinephileLog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;

    // 회원 리스트 화면
    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/user_list";
    }

    // 회원 수정 화면
    @GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow();
        List<Grade> grades = gradeRepository.findAll();

        model.addAttribute("user", user);
        model.addAttribute("grades", grades);
        return "admin/user_edit";
    }

    // 회원 수정 처리
    @PostMapping("/{id}")
    @Transactional
    public String updateUser(@PathVariable Long id,
                             @RequestParam String email,
                             @RequestParam String nickname,
                             @RequestParam Long gradeId) {
        User user = userRepository.findById(id).orElseThrow();
        user.setEmail(email);
        user.setNickname(nickname);

        Grade grade = gradeRepository.findById(gradeId).orElseThrow();
        user.setGrade(grade);

        userRepository.save(user);

        return "redirect:/admin/users"; // 수정 완료 후 스트로 이동
    }

    // 회원 삭제
    @DeleteMapping("/{id}")
    public @ResponseBody String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "OK";
    }
}