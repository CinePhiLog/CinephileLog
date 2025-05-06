package com.CinephileLog.dto;

import com.CinephileLog.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String name;

    public CustomOAuth2User(Map<String, Object> attributes, Role role, String name) {
        this.attributes = attributes;
        this.name = name;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(role.name()));
        this.attributes.put("role", role);
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

    public Role getRole() {
        return (Role) attributes.get("role");
    }
}