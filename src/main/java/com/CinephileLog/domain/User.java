package com.CinephileLog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 20)
    private String provider;

    @Column(nullable = false, length = 300)
    private String email;

    @Column(length = 100)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('ROLE_ADMIN','ROLE_USER') DEFAULT 'ROLE_USER'")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id", nullable = false)
    private Grade grade;

    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private Long point = 0L;

    @Column(nullable = false, length = 1, columnDefinition = "VARCHAR(1) DEFAULT 'Y'")
    private String isActive;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDate;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedDate;

    public User(String provider, String email, String nickname, Grade grade) {
        this.provider = provider;
        this.email = email;
        this.nickname = nickname;
        this.grade = grade;
        this.role = Role.ROLE_USER;
        this.isActive = "Y";
        this.registerDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }
}
