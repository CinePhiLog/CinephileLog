package com.CinephileLog.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewLikeResponse {
    private boolean liked;  // true : 좋아요, false : 좋아요 취소

    public ReviewLikeResponse(boolean liked) {
        this.liked = liked;
    }
}
