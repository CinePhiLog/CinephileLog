package com.CinephileLog.controller;

import com.CinephileLog.domain.ChattingRoom;
import com.CinephileLog.domain.User;
import com.CinephileLog.dto.ChatMessageDTO;
import com.CinephileLog.movie.dto.MovieResponse;
import com.CinephileLog.movie.service.MovieService;
import com.CinephileLog.service.ChattingRoomService;
import com.CinephileLog.service.MessageService;
import com.CinephileLog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Timestamp;

@Slf4j
@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChattingRoomService chattingRoomService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @MessageMapping("/chat/send/{roomId}")
    public void sendMessage(@DestinationVariable Long roomId, ChatMessageDTO message) {
        message.setSendTime(new Timestamp(System.currentTimeMillis()));

        messageService.saveMessage(message, roomId);
        template.convertAndSend("/topic/chat/" + roomId, message);
    }


    @GetMapping("/chatroom/{movieId}")
    public String enterChatRoom(@PathVariable Long movieId, Model model,@AuthenticationPrincipal OAuth2User oAuth2User) {
        Long userId = oAuth2User.getAttribute("userId");
        User user = userService.getUserById(userId);
        ChattingRoom room = chattingRoomService.findOrCreateByMovieId(movieId);
        MovieResponse movie = movieService.getMovieDetail(movieId);
        model.addAttribute("roomId", room.getRoomId());
        model.addAttribute("userId", user.getUserId());
        model.addAttribute("nickname", user.getNickname());
        model.addAttribute("movieId", movieId);
        model.addAttribute("movieTitle", movie.getTitle());
        model.addAttribute("posterUrl", movie.getPosterUrl());
        model.addAttribute("rating", movie.getRating());
        return "chatroom";
    }

    @MessageMapping("/chat/enter/{roomId}")
    public void enter(@DestinationVariable Long roomId, ChatMessageDTO message) {
        message.setType("SYSTEM");
        message.setSendTime(new Timestamp(System.currentTimeMillis()));
        template.convertAndSend("/topic/chat/" + roomId, message);
    }

    @MessageMapping("/chat/leave/{roomId}")
    public void leave(@DestinationVariable Long roomId, ChatMessageDTO message) {
        message.setType("SYSTEM");
        message.setSendTime(new Timestamp(System.currentTimeMillis()));
        template.convertAndSend("/topic/chat/" + roomId, message);
    }

//    @GetMapping("/chatroom/{movieId}")
//    public String enterChatRoom(@PathVariable Long movieId, Model model) {
//
//        // 테스트용 사용자
//        Long fakeUserId = 1L;
//        String fakeNickname = "testUser";
//
//        ChattingRoom room = chattingRoomService.findOrCreateByMovieId(movieId);
//
//        model.addAttribute("roomId", room.getRoomId());
//        model.addAttribute("userId", fakeUserId);
//        model.addAttribute("nickname", fakeNickname);
//        return "chatroom";
//    }



}