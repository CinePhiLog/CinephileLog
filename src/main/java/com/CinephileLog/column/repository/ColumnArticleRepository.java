package com.CinephileLog.column.repository;

import com.CinephileLog.column.domain.ColumnArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColumnArticleRepository extends JpaRepository<ColumnArticle, Long> {
    Page<ColumnArticle> findAllByIsDeletedFalse(Pageable pageable);

    Page<ColumnArticle> findByTitleContainingAndIsDeletedFalse(String title, Pageable pageable);

    Page<ColumnArticle> findByUser_NicknameContainingAndIsDeletedFalse(String nickname, Pageable pageable);

    Optional<ColumnArticle> findByColumnIdAndIsDeletedFalse(Long columnId);
}


