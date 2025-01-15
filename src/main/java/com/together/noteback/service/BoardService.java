package com.together.noteback.service;

import com.together.noteback.entity.BoardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.together.noteback.repository.BoardRepository;


@Service
public class BoardService {
    
    @Autowired
    private BoardRepository boardRepository;

    public void writeBoard(){
        BoardEntity board = new BoardEntity();
        board.setUsername("username");
        board.setTitle("1");
        board.setContent("abcdefg");
        boardRepository.save(board);
    }
}
