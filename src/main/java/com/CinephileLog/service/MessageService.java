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
        List<Object[]> results = messageRepository.findMessagesWithNickname(roomId, offset, size);

        return results.stream().map(row -> {
            ChatMessageDTO dto = new ChatMessageDTO();
            dto.setUserId(((Number) row[2]).longValue());
            dto.setContent((String) row[3]);
            dto.setSendTime((Timestamp) row[4]);
            dto.setNickname((String) row[5]);
            return dto;
        }).collect(Collectors.toList());
    }


}

