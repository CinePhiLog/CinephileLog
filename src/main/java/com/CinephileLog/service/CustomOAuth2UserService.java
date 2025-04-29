package com.CinephileLog.service;

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

        Map<String, Object> newAttributes = new HashMap<>();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        if (provider.equalsIgnoreCase("kakao")) {
            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

            email = (String) kakaoAccount.get("email");
            nickname = (String) properties.get("nickname");
        } else {
            email = (String) attributes.get("email");
            nickname = (String) attributes.get("name");
        }

        if (email == null || nickname == null) {
            throw new OAuth2AuthenticationException("Get user information from " + provider + " failed");
        }

        AddUserRequest addUserRequest = new AddUserRequest();
        addUserRequest.setEmail(email);
        addUserRequest.setNickname(nickname);
        addUserRequest.setProvider(provider);

        User user = userService.logIn(addUserRequest);

        newAttributes.put("userId", user.getUserId());
        newAttributes.put("email", user.getEmail());
        newAttributes.put("nickname", user.getNickname());

        return new CustomOAuth2User(
                newAttributes,
                oAuth2User.getAuthorities(),
                nickname
        );
    }

}
