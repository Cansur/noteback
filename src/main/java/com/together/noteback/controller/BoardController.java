package com.together.noteback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.together.noteback.dto.WriteBoardDTO;
import com.together.noteback.entity.BoardEntity;
import com.together.noteback.service.BoardService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/board")
public class BoardController {
    
    private final BoardService boardService;

    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    /** id를 이용하여 게시판 찾기 */
    @GetMapping("/{id}")
    public BoardEntity getBoard(@PathVariable("id") Integer id) {
        return boardService.getBoard(id);
    }
    
    /** 게시판 작성 */
    @PostMapping("/create")
    public void postBoard(@ModelAttribute WriteBoardDTO writeBoardDTO) {
        boardService.writeBoard(writeBoardDTO);
    }

    /** 게시판 수정 */
    @PutMapping("/{id}")
    public void putBoard(@PathVariable("id") Integer id, @ModelAttribute WriteBoardDTO writeBoardDTO) {
        boardService.putBoard(id, writeBoardDTO);
    }
    
    /** 게시판 삭제 */
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable("id") Integer id){
        boardService.deleteBoard(id);
    }

    // ✅ 현재 로그인한 사용자의 게시글만 반환
    @GetMapping("/my")
    public List<BoardEntity> getMyBoards(@AuthenticationPrincipal String username) {
        return boardService.getBoardsByUsername(username);
    }
}
