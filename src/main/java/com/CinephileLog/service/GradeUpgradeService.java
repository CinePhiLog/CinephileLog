package com.CinephileLog.service;

import com.CinephileLog.domain.Grade;
import com.CinephileLog.domain.User;
import com.CinephileLog.repository.GradeRepository;
import com.CinephileLog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeUpgradeService {
    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;

    @Transactional
    public void upgradeUsers() {
        List<User> activeUsers = userRepository.findByIsActive("Y");    // 가입되어 있는 회원들 불러오기
        for (User user : activeUsers) {
            // 현재 기준에 맞는 등급 조회
            Grade matchedGrade = gradeRepository.findTopByMinPointLessThanEqualOrderByMinPointDesc(user.getPoint())
                    .orElse(null);

            if (matchedGrade != null && !matchedGrade.equals(user.getGrade())) {
                user.setGrade(matchedGrade);
                userRepository.save(user);
            }
        }
    }
}
