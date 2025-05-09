package com.CinephileLog.repository;

import com.CinephileLog.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = """
    SELECT m.message_id, m.room_id, m.user_id, m.content, m.send_time, u.nickname
    FROM message m
    JOIN user u ON m.user_id = u.user_id
    WHERE m.room_id = :roomId
    ORDER BY m.send_time ASC 
    LIMIT :size OFFSET :offset
    """, nativeQuery = true)
    List<Object[]> findMessagesWithNickname(@Param("roomId") Long roomId,
                                            @Param("offset") int offset,
                                            @Param("size") int size);

}


