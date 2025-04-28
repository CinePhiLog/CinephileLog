package com.CinephileLog.controller;

import com.CinephileLog.domain.ChattingRoom;
import com.CinephileLog.dto.ChatMessageDTO;
import com.CinephileLog.service.ChattingRoomService;
import com.CinephileLog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Timestamp;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChattingRoomService chattingRoomService;

    @MessageMapping("/chat/send/{roomId}")
    public void sendMessage(@DestinationVariable Long roomId, ChatMessageDTO message) {
        message.setSendTime(new Timestamp(System.currentTimeMillis()));

        messageService.saveMessage(message, roomId);
        template.convertAndSend("/topic/chat/" + roomId, message);
    }


    //    @GetMapping("/chatroom/{movieId}")
//    public String enterChatRoom(@PathVariable Long movieId, Model model, Principal principal) {
//        User user = userService.findByEmail(principal.getName());
//        Grade grade = user.getGrade();
//        if (grade.getMinPoint() >= REQUIRED_CHAT_POINT) {
//            ChattingRoom room = chattingRoomService.findOrCreateByMovieId(movieId);
//            model.addAttribute("roomId", room.getRoomId());
//            model.addAttribute("userId", user.getUserId());
//            model.addAttribute("nickname", user.getNickname());
//            return "chatroom";
//        } else {
//            return "redirect:/error/grade-insufficient";
//        }
//    }
    @GetMapping("/chatroom/{movieId}")
    public String enterChatRoom(@PathVariable Long movieId, Model model) {

        // 테스트용 사용자
        Long fakeUserId = 1L;
        String fakeNickname = "testUser";

        // 채팅방 없으면 생성
        ChattingRoom room = chattingRoomService.findOrCreateByMovieId(movieId);

        model.addAttribute("roomId", room.getRoomId());
        model.addAttribute("userId", fakeUserId);
        model.addAttribute("nickname", fakeNickname);
        return "chatroom";
    }

}
