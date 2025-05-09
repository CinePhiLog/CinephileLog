package com.CinephileLog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserScoreDTO {
    private Long userId;
    private int reviewCount;
    private int likeCount;
    private double weightedScore;
    private Long currentGradeId;
}
