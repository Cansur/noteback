package com.together.noteback.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.together.noteback.dto.WriteBoardDTO;
import com.together.noteback.entity.BoardEntity;
import com.together.noteback.jwt.JWTUtil;
import com.together.noteback.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/board")
public class BoardController {
    
    private final BoardService boardService;
    private final JWTUtil jwtUtil;

    public BoardController(BoardService boardService, JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
        this.boardService = boardService;
    }

    /** id를 이용하여 게시판 찾기 */
    @GetMapping("/{id}")
    public BoardEntity getBoard(@PathVariable("id") Integer id, HttpServletRequest request) {
        String token = extractToken(request);
        String username = jwtUtil.getUsername(token);
        return boardService.getBoard(id, username);
    }
    
    /** 게시판 작성 */
    @PostMapping("/create")
    public Integer postBoard(@RequestBody WriteBoardDTO writeBoardDTO, HttpServletRequest request) {
        String token = extractToken(request);
        String username = jwtUtil.getUsername(token);
        return boardService.writeBoard(writeBoardDTO, username);
    }

    /** 게시판 수정 */
    @PutMapping("/{id}")
    public void putBoard(@PathVariable("id") Integer id, @RequestBody WriteBoardDTO writeBoardDTO) {
        boardService.putBoard(id, writeBoardDTO);
    }
    
    /** 게시판 삭제 */
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable("id") Integer id){
        boardService.deleteBoard(id);
    }

    // ✅ 현재 로그인한 사용자의 게시글만 반환
    @GetMapping("/my")
    public List<BoardEntity> getMyBoards(HttpServletRequest request) {
        String token = extractToken(request);
        String username = jwtUtil.getUsername(token);
        return boardService.getBoardsByUsername(username);
    }

    private String extractToken(HttpServletRequest request) {
        String token = request.getHeader("access");
        if (token == null) {
            throw new IllegalArgumentException("토큰이 없습니다.");
        }
        return token;
    }
}
