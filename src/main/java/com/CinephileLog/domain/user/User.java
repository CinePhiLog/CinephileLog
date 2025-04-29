package com.CinephileLog.domain.user;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String email;
    private String password;
    private String nickname;
    private LocalDateTime registerDate;
    private Long point;
    private String isActive;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
