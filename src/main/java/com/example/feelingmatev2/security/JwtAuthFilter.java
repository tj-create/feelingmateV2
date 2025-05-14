package com.example.feelingmatev2.security;

import com.example.feelingmatev2.security.tokenblacklist.TokenBlackListRepository;
import com.example.feelingmatev2.security.userdetails.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenBlackListRepository tokenBlackListRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (tokenBlackListRepository.existsByToken(token)) {
                throw new RuntimeException("로그아웃된 사용자");
            }

            try {
                String loginId = jwtUtil.extractId(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                // 로그만 남기고 계속 진행 (필요하면 예외 처리 추가)
                System.out.println("JWT 인증 실패: " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
