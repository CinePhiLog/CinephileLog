package com.CinephileLog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Grade {
    @Id
    @Column(updatable = false)
    private Long gradeId;

    @Column(nullable = false)
    private String gradeName;

    @Column(nullable = false)
    private String minPoint;

    @Column(nullable = false)
    private String description;
}
