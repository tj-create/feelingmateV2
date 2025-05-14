package com.example.feelingmatev2.security;

import com.example.feelingmatev2.security.tokenblacklist.TokenBlackList;
import com.example.feelingmatev2.security.tokenblacklist.TokenBlackListRepository;
import com.example.feelingmatev2.user.User;
import com.example.feelingmatev2.user.UserRepository;
import com.example.feelingmatev2.user.dto.LoginRequest;
import com.example.feelingmatev2.user.dto.SignupRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenBlackListRepository tokenBlackListRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void signup(SignupRequest signupRequest) {
        if (userRepository.existsByLoginId(signupRequest.loginId())) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

        User user = User.createUser(signupRequest);
        user.encodePassword(passwordEncoder.encode(signupRequest.password()));

        userRepository.save(user);
    }

    public String login(LoginRequest loginRequest) {
        User findUser = userRepository.findUserByLoginId(loginRequest.loginId()).orElseThrow(() -> new RuntimeException("아이디 또는 비밀번호가 일치하지 않습니다."));
        System.out.println("password = " + findUser.getPassword());

        if (!passwordEncoder.matches(loginRequest.password(), findUser.getPassword())) {
            throw new IllegalStateException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.generateToken(loginRequest.loginId());
    }

    public void logout(String token) {
        LocalDateTime extractExpiration = jwtUtil.getExtractExpiration(token);
        tokenBlackListRepository.save(new TokenBlackList(token, extractExpiration));
    }

}
