package com.CinephileLog.controller;

import com.CinephileLog.domain.User;
import com.CinephileLog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserViewController {

    private final UserService userService;

    public UserViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String logInView() {
        return "logIn";
    }

    @PostMapping("/userLogout")
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "redirect:/login";   //redirect to log in page after log out
    }

    @GetMapping("/profile")
    public String myProfileView(@AuthenticationPrincipal OAuth2User user, Model model) {
        User userInfo = userService.getUserById(user.getAttribute("userId"));
        model.addAttribute("user", userInfo);

        return "myProfile";
    }

    @GetMapping("/home")
    public String homeView() {
        return "index";
    }
}
