package com.CinephileLog.controller;

import com.CinephileLog.dto.CustomOAuth2User;
import com.CinephileLog.domain.User;
import com.CinephileLog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")       // 관리자 홈 (localhost:8080/admin)
public class AdminHomeController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String home(@AuthenticationPrincipal CustomOAuth2User principal, Model model) {
        if (principal != null) {
            model.addAttribute("email", principal.getAttribute("email"));
            model.addAttribute("userId", principal.getAttribute("userId"));
            model.addAttribute("role", principal.getRole().toString());

            if (principal.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                model.addAttribute("isAdmin", true);
            } else {
                model.addAttribute("isAdmin", false);
            }

            Object userIdObject = principal.getAttribute("userId");
            if (userIdObject != null) {
                String userId = String.valueOf(userIdObject);
                User userInfo = userService.getUserById(Long.valueOf(userId));
                model.addAttribute("user", userInfo);
            }
        }
        model.addAttribute("showMenu", true);
        return "admin/home";
    }
}