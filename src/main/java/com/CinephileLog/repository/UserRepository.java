package com.CinephileLog.repository;

import com.CinephileLog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndProviderAndIsActive(String email, String provider, String isActive);
    List<User> findByNicknameContaining(String keyword);
    User findByNickname(String nickname);
}