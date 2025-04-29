package com.CinephileLog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long userId;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(insertable = false)
    private String point;

    @Column(insertable = false)
    private String isActive;

    @CreatedDate
    private LocalDateTime registerDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name="roleId")
    private Role role;

    @ManyToOne
    @JoinColumn(name="gradeId")
    private Grade grade;

    public User(String provider, String email, String nickname, Role role, Grade grade) {
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.grade = grade;
        this.provider = provider;
    }
}
