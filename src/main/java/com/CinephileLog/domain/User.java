package com.CinephileLog.domain;

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

    private String provider;
    private String email;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    private Long point;
    private String isActive;
    private LocalDateTime registerDate;
    private LocalDateTime updatedDate;
}


