package com.CinephileLog.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long userId;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String email;

    @Column
    private String nickname;

    @Column
    private String role = "ROLE_USER";

    @Column
    private Long point = 0L;

    @Column
    private String isActive = "Y";

    @CreatedDate
    private LocalDateTime registerDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name="gradeId")
    private Grade grade;

    public User(String provider, String email, String nickname, Grade grade) {
        this.provider = provider;
        this.email = email;
        this.nickname = nickname;
        this.grade = grade;
    }
}


