package com.CinephileLog.service;

import com.CinephileLog.domain.Grade;
import com.CinephileLog.domain.User;
import com.CinephileLog.dto.AddUserRequest;
import com.CinephileLog.repository.GradeRepository;
import com.CinephileLog.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;

    public UserService(UserRepository userRepository, GradeRepository gradeRepository) {
        this.userRepository = userRepository;
        this.gradeRepository = gradeRepository;
    }

    public User getActiveUserByEmailAndProvider(String email, String provider) {
        return userRepository.findByEmailAndProviderAndIsActive(email, provider,"Y");
    }

    public User signUp(AddUserRequest request) {
        Grade grade= gradeRepository.findFirstByOrderByGradeIdAsc();

        if (grade == null) {
            throw new IllegalArgumentException("There's no grade data available");
        }

        User user = new User(request.getProvider(), request.getEmail(), request.getNickname(), grade);
        userRepository.save(user);

        return user;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public void updateUserById(Long userId, AddUserRequest addUserRequest) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            String nickname = addUserRequest.getNickname();
            String isActive = addUserRequest.getIsActive();

            if (nickname != null) {
                user.setNickname(nickname);
            }

            if (isActive != null && !isActive.isEmpty()) {
                user.setIsActive(addUserRequest.getIsActive());
            }

            userRepository.save(user);
        }
    }

    public User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }
}
