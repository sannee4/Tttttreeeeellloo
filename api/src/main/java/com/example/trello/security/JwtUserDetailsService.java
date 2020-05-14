package com.example.trello.security;

import com.example.trello.models.UserEntity;
import com.example.trello.security.jwt.JwtUser;
import com.example.trello.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {


    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userService.findByUsername(username);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        JwtUser jwtUser = JwtUser.create(user.get());
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return jwtUser;
    }
}
