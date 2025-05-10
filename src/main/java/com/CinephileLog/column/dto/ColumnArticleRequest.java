package com.CinephileLog.column.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnArticleRequest {
    private Long columnId;
    private Long movieId;
    private String title;
    private String content;
    private String movieTitle;
}
