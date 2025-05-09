package com.CinephileLog.column.domain;

import com.CinephileLog.domain.User;
import com.CinephileLog.movie.domain.Movie;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "column_article")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long columnId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Timestamp createdDate;

    private Timestamp updatedDate;

    private Boolean isDeleted = false;

    private Long viewCount = 0L;
}
