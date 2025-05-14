package com.example.feelingmatev2.security.tokenblacklist;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class TokenBlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    private LocalDateTime extractExpiration;

    public TokenBlackList(String token, LocalDateTime extractExpiration) {
        this.token = token;
        this.extractExpiration = extractExpiration;
    }

    public TokenBlackList() {
    }
}
