package com.CinephileLog.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "Grade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Grade {
    @Id
    @Column(updatable = false)
    private Long gradeId;

    @Column(nullable = false)
    private String gradeName;

    @Column(nullable = false)
    private Long minPoint;

    @Column(nullable = false)
    private String description;
}