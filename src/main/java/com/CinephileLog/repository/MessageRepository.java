package com.CinephileLog.repository;

import com.CinephileLog.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT * FROM message WHERE room_id = :roomId ORDER BY send_time DESC LIMIT :size OFFSET :offset", nativeQuery = true)
    List<Message> findMessages(@Param("roomId") Long roomId,
                               @Param("offset") int offset,
                               @Param("size") int size);
}


