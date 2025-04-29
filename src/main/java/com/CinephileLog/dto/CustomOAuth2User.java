package com.CinephileLog.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String name;

    public CustomOAuth2User(Map<String, Object> attributes, Collection<? extends GrantedAuthority> authorities, String name) {
        this.attributes = attributes;
        this.authorities = authorities;
        this.name = name;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }

    public String getNickname() {
        return (String) attributes.get("nickname");
    }

    public String getFullName() {
        return (String) attributes.get("name");
    }
}
