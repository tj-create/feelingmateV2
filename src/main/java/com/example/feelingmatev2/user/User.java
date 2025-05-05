package com.example.feelingmatev2.user;

import com.example.feelingmatev2.user.dto.SignupRequest;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String nickname;

    private final LocalDateTime createAt = LocalDateTime.now();

    public User() {
    }


    public static User createUser(SignupRequest signupRequest) {
        User user = new User();
        user.loginId = signupRequest.loginId();
        user.nickname = signupRequest.nickname();

        return user;
    }

    public void encodePassword(String encodePassword) {
        this.password = encodePassword;
    }
}
