package com.CinephileLog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    private Long roomId;
    private Long userId;
    private Timestamp sendTime;

    @Column(columnDefinition = "TEXT")
    private String content;
}