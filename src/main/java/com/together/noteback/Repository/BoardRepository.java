package com.together.noteback.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.together.noteback.data.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
}
