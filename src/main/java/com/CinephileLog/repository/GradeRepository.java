package com.CinephileLog.repository;

import com.CinephileLog.domain.user.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findTopByMinPointLessThanEqualOrderByMinPointDesc(Long point);
}