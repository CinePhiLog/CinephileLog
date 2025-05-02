package com.CinephileLog.service;

import com.CinephileLog.domain.Grade;
import com.CinephileLog.domain.User;
import com.CinephileLog.dto.AddUserRequest;
import com.CinephileLog.repository.GradeRepository;
import com.CinephileLog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

            /*
            if (nickname != null && !nickname.isEmpty()) {
                User userByNickname = userRepository.findByNickname(addUserRequest.getNickname());
                if (userByNickname != null) {
                    if (!userByNickname.getUserId().equals(userId)) {
                        throw new IllegalArgumentException("Nickname already exists. Please choose another nickname");
                    }
                }
                user.setNickname(nickname);
            }
            */

            if (isNicknameValid(addUserRequest.getNickname(),userId)) {
                user.setNickname(nickname);
            } else {
                throw new IllegalArgumentException("Nickname already exists. Please choose another nickname");
            }

            if (isActive != null && !isActive.isEmpty()) {
                user.setIsActive(addUserRequest.getIsActive());
            }

            userRepository.save(user);
        }
    }

    public Optional<User> getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    public boolean isNicknameValid(String nickname, Long userId) {
        Optional<User> user = userRepository.findByNickname(nickname);
        boolean isValid = true;

        if (user != null) {
            isValid = false;

            //it is valid if it's used by the same user id
            if (userId != null) {
                if (user.get().equals(userId)) {
                    isValid = true;
                }
            }
        }
        return isValid;
    }
}
