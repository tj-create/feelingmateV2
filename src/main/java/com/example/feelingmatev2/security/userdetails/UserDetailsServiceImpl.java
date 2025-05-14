package com.example.feelingmatev2.security.userdetails;

import com.example.feelingmatev2.security.UserDetailsImpl;
import com.example.feelingmatev2.user.User;
import com.example.feelingmatev2.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findUserByLoginId(loginId).orElseThrow(
                () -> new UsernameNotFoundException("사용자 없음")
        );

        return new UserDetailsImpl(user);
    }
}
