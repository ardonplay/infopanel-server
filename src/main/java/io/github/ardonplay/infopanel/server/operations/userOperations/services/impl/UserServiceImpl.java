package io.github.ardonplay.infopanel.server.operations.userOperations.services.impl;

import io.github.ardonplay.infopanel.server.common.entities.User;
import io.github.ardonplay.infopanel.server.operations.userOperations.models.enums.UserRole;
import io.github.ardonplay.infopanel.server.operations.userOperations.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {


    @Override
    public Optional<User> createUser(String username, String password, UserRole userRole) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getUserId(Integer id) {
        return Optional.empty();
    }


    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
