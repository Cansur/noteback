package com.together.noteback.service;

import com.together.noteback.dto.WriteBoardDTO;
import com.together.noteback.entity.BoardEntity;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.together.noteback.repository.BoardRepository;


@Service
public class BoardService {
    
    @Autowired
    private BoardRepository boardRepository;

    /** 게시판 작성 */
    public void writeBoard(WriteBoardDTO writeBoardDTO){
        BoardEntity board = new BoardEntity();
        board.setUsername(writeBoardDTO.getUsername());
        board.setTitle(writeBoardDTO.getTitle());
        board.setContent(writeBoardDTO.getContent());
        boardRepository.save(board);
    }

    /** 게시판 보기 */
    public BoardEntity getBoard(Integer id){
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("can't find board"));
    }

    /** 게시판 수정 */
    public void putBoard(Integer id, WriteBoardDTO writeBoardDTO){
        BoardEntity board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("can't find board"));
        board.setTitle(writeBoardDTO.getTitle());
        board.setContent(writeBoardDTO.getContent());
        board.setUpdated_at(LocalDateTime.now());
        boardRepository.save(board);
    }

    /** 게시판 삭제 */
    public void deleteBoard(Integer id){
        boardRepository.deleteById(id);
    }


    
}
