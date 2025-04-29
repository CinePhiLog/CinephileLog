package com.CinephileLog.controller;

import com.CinephileLog.dto.ChatMessageDTO;
import com.CinephileLog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatMessageRestController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public List<ChatMessageDTO> getMessages(@RequestParam Long roomId,
                                            @RequestParam int page,
                                            @RequestParam int size) {
        return messageService.getMessages(roomId, page, size);
    }
}
