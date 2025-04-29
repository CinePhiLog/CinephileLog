package com.CinephileLog.repository;

import com.CinephileLog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByIsActive(String isActive); // 가입 중인 회원 조회
}
