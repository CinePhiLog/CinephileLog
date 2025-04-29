package com.CinephileLog.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Role {
    @Id
    @Column(updatable = false)
    private Long roleId;

    @Column(nullable = false)
    private String roleName;
}
