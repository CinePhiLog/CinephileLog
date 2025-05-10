package com.CinephileLog.column.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnArticleResponse {
    private Long columnId;
    private Long movieId;
    private String title;
    private String content;
    private String nickname;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private Long viewCount;
    private String posterUrl;
    private String movieTitle;

}