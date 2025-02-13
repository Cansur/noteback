package com.together.noteback.service;

import com.together.noteback.dto.CustomUserDetails;
import com.together.noteback.entity.UserEntity;
import com.together.noteback.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService 은 Spring Security에서 유저의 정보를 가져오는 인터페이스
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userData = userRepository.findByUsername(username);

        if(userData != null) {

            return new CustomUserDetails(userData);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        // return null;
    }
}
