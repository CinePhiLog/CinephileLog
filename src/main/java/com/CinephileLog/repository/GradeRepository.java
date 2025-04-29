package com.CinephileLog.repository;

import com.CinephileLog.domain.user.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.CinephileLog.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findTopByMinPointLessThanEqualOrderByMinPointDesc(Long point);
    Grade findFirstByOrderByGradeIdAsc();
}