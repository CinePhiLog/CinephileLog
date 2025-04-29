package com.CinephileLog.service;

import com.CinephileLog.domain.Message;
import com.CinephileLog.dto.ChatMessageDTO;
import com.CinephileLog.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired private MessageRepository messageRepository;

    public void saveMessage(ChatMessageDTO dto, Long roomId) {
        Message msg = new Message();
        msg.setRoomId(roomId);
        msg.setUserId(dto.getUserId());
        msg.setSendTime(new Timestamp(System.currentTimeMillis()));
        msg.setContent(dto.getContent());
        messageRepository.save(msg);
    }

    public List<ChatMessageDTO> getMessages(Long roomId, int page, int size) {
        int offset = page * size;
        return messageRepository.findMessages(roomId, offset, size).stream().map(m -> {
            ChatMessageDTO dto = new ChatMessageDTO();
            dto.setUserId(m.getUserId());
            dto.setNickname("User" + m.getUserId());
            dto.setContent(m.getContent());
            dto.setSendTime(m.getSendTime());
            return dto;
        }).collect(Collectors.toList());
    }

}

