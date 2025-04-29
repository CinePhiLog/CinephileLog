package com.CinephileLog.repository;

import com.CinephileLog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.CinephileLog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByIsActive(String isActive); // 활동 중인 회원만 조회
    User findByEmailAndProviderAndIsActive(String email, String provider, String isActive);
}
