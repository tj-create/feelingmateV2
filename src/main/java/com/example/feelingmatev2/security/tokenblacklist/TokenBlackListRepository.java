package com.example.feelingmatev2.security.tokenblacklist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Long> {

    boolean existsByToken(String token);
}
