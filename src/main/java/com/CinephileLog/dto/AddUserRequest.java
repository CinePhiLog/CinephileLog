package com.CinephileLog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    private String provider;
    private String email;
    private String nickname;
    private String isActive;
    private Long roleId;
    private Long gradeId;
}
