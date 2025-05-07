package com.CinephileLog.service;

import com.CinephileLog.domain.Role;
import com.CinephileLog.domain.User;
import com.CinephileLog.dto.AddUserRequest;
import com.CinephileLog.dto.CustomOAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserService userService;

    public CustomOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String email = null;
        String nickname = null;

        Map<String, Object> originalAttributes = oAuth2User.getAttributes();
        Map<String, Object> newAttributes = new HashMap<>(originalAttributes);

        if (provider.equalsIgnoreCase("kakao")) {
            Map<String, Object> properties = (Map<String, Object>) originalAttributes.get("properties");
            Map<String, Object> kakaoAccount = (Map<String, Object>) originalAttributes.get("kakao_account");
            email = (String) kakaoAccount.get("email");
            nickname = (String) properties.get("nickname");
            newAttributes.put("email", email);
            newAttributes.put("nickname", nickname);
        } else {
            email = (String) originalAttributes.get("email");
            nickname = (String) originalAttributes.get("name");
            newAttributes.put("email", email);
            newAttributes.put("nickname", nickname);
        }

        if (email == null || nickname == null) {
            throw new OAuth2AuthenticationException("Get user information from " + provider + " failed");
        }

        User activeUser = userService.getActiveUserByEmailAndProvider(email, provider);
        Role userRole;

        if (activeUser == null) {
            AddUserRequest addUserRequest = new AddUserRequest();
            addUserRequest.setEmail(email);
            addUserRequest.setProvider(provider);

            User user = userService.signUp(addUserRequest);
            user.setRole(Role.ROLE_USER);
            userService.save(user);
            userRole = user.getRole();

            newAttributes.put("userId", user.getUserId());
            newAttributes.put("email", user.getEmail());
            newAttributes.put("nickname", user.getNickname());
            newAttributes.put("role", user.getRole());
        } else {
            newAttributes.put("userId", activeUser.getUserId());
            newAttributes.put("email", activeUser.getEmail());
            newAttributes.put("nickname", activeUser.getNickname());
            newAttributes.put("role", activeUser.getRole());
            userRole = activeUser.getRole();
        }

        return new CustomOAuth2User(
                newAttributes,
                userRole,
                nickname
        );
    }
}