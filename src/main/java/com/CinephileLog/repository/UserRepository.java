package com.CinephileLog.repository;

import com.CinephileLog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndProviderAndIsActive(String email, String provider, String isActive);
    List<User> findByNicknameContaining(String keyword);    // 키워드로 닉네임 검색
    Optional<User> findByNickname(String nickname);
}