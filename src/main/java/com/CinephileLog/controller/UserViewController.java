package com.CinephileLog.controller;

import com.CinephileLog.domain.User;
import com.CinephileLog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


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

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @PostMapping("/userLogout")
    public void logOut(HttpServletRequest request,
                       HttpServletResponse response,
                       @AuthenticationPrincipal OAuth2User user,
                       Authentication authentication) throws IOException {
        if (user == null) {
            response.sendRedirect("/login"); //If the user is not authenticated (OAuth2User is null), redirect to login page
            return;
        }

        // The user is authenticated, so we can proceed with logout logic
        new SecurityContextLogoutHandler().logout(request, response, null); // Log out the user internally from Spring

        if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
            String provider = oauth2Token.getAuthorizedClientRegistrationId();
            String clientId = clientRegistrationRepository.findByRegistrationId(provider).getClientId();

            String logoutRedirectUri = "http://localhost:8080/postLogout";
            String encodedLogoutRedirectUri = URLEncoder.encode(logoutRedirectUri, StandardCharsets.UTF_8);

            //Define logout URL per provider and redirect
            String logoutUrl = switch (provider) {
                case "google" -> "https://accounts.google.com/Logout?continue=https://appengine.google.com/_ah/logout?continue=" + encodedLogoutRedirectUri;
                case "kakao" -> "https://kauth.kakao.com/oauth/logout?client_id=" + clientId + "&logout_redirect_uri=" + encodedLogoutRedirectUri;
                case "facebook" -> logoutRedirectUri;   //don't rely on Facebook's logout as it doesn't redirect back to postLogout
                default -> encodedLogoutRedirectUri;
            };

            response.sendRedirect(logoutUrl);
        }
    }

    @GetMapping("/postLogout")
    public String postLogout() {
        return "login";
    }

    @GetMapping("/checkNickname")
    public String checkNickname(@AuthenticationPrincipal OAuth2User user) {
        if (user.getAttribute("nickname") == null) {
            return "redirect:/setUpNickname";   //redirect to set up nickname page if user hasn't set up
        }

        return "redirect:/home";
    }

    @GetMapping("/setUpNickname")
    public String setUpNickname(@AuthenticationPrincipal OAuth2User user, Model model) {
        Long userId = user.getAttribute("userId");
        model.addAttribute("userId", userId);
        return "setUpNickname";
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
