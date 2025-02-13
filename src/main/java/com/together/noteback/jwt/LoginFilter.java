package com.together.noteback.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.together.noteback.dto.CustomUserDetails;
import com.together.noteback.entity.RefreshEntity;
import com.together.noteback.repository.RefreshRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * 로그인 요청을 처리하는 필터
 * <p>
 * /api/login 경로로 POST 요청이 오면 LoginFilter가 가로챕니다.
 * <p>
 * form-data로 전달된 username과 password를 추출하고 UsernamePasswordAuthenticationToken을
 * 생성하여 AuthenticationManager에게 전달합니다.
 * <p>
 * 성공 시 Access 토큰과 Refresh 토큰을 발급하고 응답 헤더와 쿠키에 담아 반환합니다.
 * <p>
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    private RefreshRepository refreshRepository;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
            RefreshRepository refreshRepository) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        setFilterProcessesUrl("/api/login"); // 변경된 경로 설정
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            // JSON 데이터를 직접 읽어오기
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> requestBody = objectMapper.readValue(request.getInputStream(), Map.class);

            String username = requestBody.get("username");
            String password = requestBody.get("password");

            // 스프링 시큐리티에서 username과 password를 검증하기 위해서 token에 담기
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password,
                    null);

            // token에 담은 검증을 위한 AuthenticationManager로 전달
            return authenticationManager.authenticate(authToken);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) {

        // 유저 정보
        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 토큰 생성
        String access = jwtUtil.createJwt("access", username, role, 600000L);
        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        // Refresh 토큰 저장
        addRefreshEntity(username, refresh, 86400000L);

        // 응답 설정
        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    // 로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) {

        // 로그인 실패시 401 응답 코드 반환
        response.setStatus(401);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        // cookie.setSecure(true);
        // cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}
