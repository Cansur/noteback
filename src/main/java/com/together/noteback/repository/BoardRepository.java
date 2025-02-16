package com.together.noteback.repository;

import com.together.noteback.entity.BoardEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    // ✅ 특정 username의 게시글만 조회
    List<BoardEntity> findByUsername(String username);
}
