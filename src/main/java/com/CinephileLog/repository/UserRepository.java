package com.CinephileLog.repository;

import com.CinephileLog.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByIsActive(String isActive); // 활동 중인 회원만 조회
}
