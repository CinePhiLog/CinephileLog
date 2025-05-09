package com.CinephileLog.service;

import com.CinephileLog.domain.Grade;
import com.CinephileLog.domain.User;
import com.CinephileLog.dto.UserScoreDTO;
import com.CinephileLog.mapper.UserScoreMapper;
import com.CinephileLog.repository.GradeRepository;
import com.CinephileLog.repository.UserRepository;
import com.CinephileLog.review.repository.ReviewLikeRepository;
import com.CinephileLog.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GradeService {     // 사용자 등급 조회

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final UserScoreMapper userScoreMapper;
    private final GradeRepository gradeRepository;

    @Autowired
    public GradeService(GradeRepository gradeRepository, UserRepository userRepository, ReviewRepository reviewRepository, ReviewLikeRepository reviewLikeRepository, UserScoreMapper userScoreMapper) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.reviewLikeRepository = reviewLikeRepository;
        this.userScoreMapper = userScoreMapper;
        this.gradeRepository = gradeRepository;
    }

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public Optional<Grade> getGradeById(Long gradeId) {
        return gradeRepository.findById(gradeId);
    }

    @Transactional
    public void updateGradeForUser(Long userId) {
        int reviewCount = reviewRepository.countByUserUserId(userId);
        int likeCount = reviewLikeRepository.countByUserId(userId);

        User user = userRepository.findById(userId).orElseThrow();

        if (user.getGrade().getGradeId() < 2 && reviewCount >= 10) {
            user.setGrade(new Grade(2L, "coke"));
        } else if (user.getGrade().getGradeId() < 3 && reviewCount >= 30 && likeCount >= 10) {
            user.setGrade(new Grade(3L, "nachos"));
        }

        userRepository.save(user);
    }

    @Scheduled(cron = "0 0 0 * * MON")
    @Transactional
    public void weeklyGradeReevaluation() {
        List<UserScoreDTO> users = userScoreMapper.selectEligibleUsers();

        for (UserScoreDTO u : users) {
            double score = u.getReviewCount() * 0.3 + u.getLikeCount() * 0.7;
            u.setWeightedScore(score);
            userScoreMapper.upsertUserScore(u);
            userRepository.updateUserPoint(u.getUserId(), (long) score);
        }

        users.sort(Comparator.comparingDouble(UserScoreDTO::getWeightedScore).reversed());

        int top10 = (int) Math.ceil(users.size() * 0.10);
        int top5 = (int) Math.ceil(users.size() * 0.05);

        Set<Long> hotdogIds = new HashSet<>();
        Set<Long> popcornIds = new HashSet<>();

        for (int i = 0; i < users.size(); i++) {
            if (i < top5) popcornIds.add(users.get(i).getUserId());
            if (i < top10) hotdogIds.add(users.get(i).getUserId());
        }

        for (UserScoreDTO dto : users) {
            Long id = dto.getUserId();
            if (popcornIds.contains(id)) {
                userRepository.updateGrade(id, 5L);
            } else if (hotdogIds.contains(id)) {
                userRepository.updateGrade(id, 4L);
            } else if (dto.getCurrentGradeId() >= 4) {
                userRepository.updateGrade(id, 3L);
            }
        }
    }
}