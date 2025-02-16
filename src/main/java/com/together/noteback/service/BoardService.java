package com.together.noteback.service;

import com.together.noteback.dto.WriteBoardDTO;
import com.together.noteback.entity.BoardEntity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.together.noteback.repository.BoardRepository;


@Service
public class BoardService {
    
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

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

    /** ✅ 특정 사용자의 게시글만 가져오기 */
    public List<BoardEntity> getBoardsByUsername(String username){
        return boardRepository.findByUsername(username);
    }
    
}
