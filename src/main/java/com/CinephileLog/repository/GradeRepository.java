package com.CinephileLog.repository;

import com.CinephileLog.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    Optional<Grade> findTopByMinPointLessThanEqualOrderByMinPointDesc(Long point);  // 받은 포인트에서 가장 높은 등급 찾아줌
}