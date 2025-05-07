package com.CinephileLog.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewListDTO {
    private Long reviewId;
    private String nickname;
    private String content;
    private LocalDateTime createdDate;

    public ReviewListDTO(Long reviewId, String nickname, String content, LocalDateTime createdDate) {
        this.reviewId = reviewId;
        this.nickname = nickname;
        this.content = content;
        this.createdDate = createdDate;
    }
}
