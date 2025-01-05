package com.together.noteback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.together.noteback.Repository.BoardRepository;
import com.together.noteback.data.Board;

@Service
public class BoardService {
    
    @Autowired
    private BoardRepository boardRepository;

    public void writeBoard(){
        Board board = new Board();
        board.setUsername("username");
        board.setTitle("1");
        board.setContent("abcdefg");
        boardRepository.save(board);
    }
}
