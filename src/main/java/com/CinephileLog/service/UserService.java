package com.CinephileLog.service;

import com.CinephileLog.domain.Grade;
import com.CinephileLog.domain.Role;
import com.CinephileLog.domain.User;
import com.CinephileLog.dto.AddUserRequest;
import com.CinephileLog.repository.GradeRepository;
import com.CinephileLog.repository.RoleRepository;
import com.CinephileLog.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GradeRepository gradeRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, GradeRepository gradeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.gradeRepository = gradeRepository;
    }

    public User logIn(AddUserRequest request) {
        User existingUser = userRepository.findByEmailAndProvider(request.getEmail(), request.getProvider());

        if (existingUser != null && existingUser.getIsActive().equals("Y")) {
            return existingUser;
        } else { //Create new account when user is not in DB or no longer active
            Role role = roleRepository.findFirstByOrderByRoleIdAsc();
            if (role == null) {
                throw new IllegalArgumentException("There's no role data available");
            }

            Grade grade= gradeRepository.findFirstByOrderByGradeIdAsc();
            if (grade == null) {
                throw new IllegalArgumentException("There's no grade data available");
            }

            User user = new User(request.getProvider(), request.getEmail(), request.getNickname(), role, grade);
            userRepository.save(user);

            return user;
        }
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public void terminateUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            user.setIsActive("N");
            userRepository.save(user);
        }
    }
}
