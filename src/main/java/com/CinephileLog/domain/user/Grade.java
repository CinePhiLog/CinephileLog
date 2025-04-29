package com.CinephileLog.domain.user;

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
    private Long gradeId;

    private String gradeName;
    private Long minPoint;
    private String description;
}