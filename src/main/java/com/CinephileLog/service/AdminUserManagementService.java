package com.CinephileLog.service;

import com.CinephileLog.domain.User;
import com.CinephileLog.mapper.UserScoreMapper;
import com.CinephileLog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AdminUserManagementService {

    private final UserRepository userRepository;
    private final UserScoreMapper userScoreMapper;

    @Autowired
    public AdminUserManagementService(UserRepository userRepository, UserScoreMapper userScoreMapper) {
        this.userRepository = userRepository;
        this.userScoreMapper = userScoreMapper;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다."));
    }

    @Transactional
    public void updateUser(Long userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다."));

        existingUser.setNickname(updatedUser.getNickname());
        existingUser.setGrade(updatedUser.getGrade());
        // 포인트 0 이상인지 확인
        if (updatedUser.getPoint() < 0) {
            throw new IllegalArgumentException("포인트는 0 이상이어야 합니다.");
        } else {
            existingUser.setPoint(updatedUser.getPoint());
        }
        existingUser.setIsActive(updatedUser.getIsActive());
        existingUser.setUpdatedDate(LocalDateTime.now());
        existingUser.setRole(updatedUser.getRole());

        userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userScoreMapper.deleteUserById(userId);
        userRepository.deleteById(userId);
    }

    public List<User> searchUsersByKeyword(String keyword) {
        return userRepository.findByNicknameContaining(keyword);
    }
}