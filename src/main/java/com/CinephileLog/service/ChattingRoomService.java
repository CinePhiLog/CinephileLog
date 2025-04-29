package com.CinephileLog.service;

import com.CinephileLog.domain.ChattingRoom;
import com.CinephileLog.repository.ChattingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


@Service
public class ChattingRoomService {

    @Autowired
    private ChattingRoomRepository chattingRoomRepository;

    public ChattingRoom findOrCreateByMovieId(Long movieId) {
        return chattingRoomRepository.findByMovieId(movieId)
                .orElseGet(() -> {
                    ChattingRoom room = new ChattingRoom();
                    room.setMovieId(movieId);
                    room.setRoomName("영화 채팅방 " + movieId);
                    room.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                    return chattingRoomRepository.save(room);
                });
    }
}
