package io.github.ardonplay.infopanel.server.operations.userOperations.services.impl;

import io.github.ardonplay.infopanel.server.common.entities.user.UserEntity;
import io.github.ardonplay.infopanel.server.common.repositories.UserRepository;
import io.github.ardonplay.infopanel.server.common.services.TypeCacheService;
import io.github.ardonplay.infopanel.server.operations.userOperations.models.enums.UserRole;
import io.github.ardonplay.infopanel.server.operations.userOperations.services.UserService;
import io.github.ardonplay.infopanel.server.operations.userOperations.services.exceptions.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final TypeCacheService cacheService;

    @Override
    public Optional<UserEntity> createUser(String username, String password, UserRole userRole) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("User already exist");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        UserEntity user = UserEntity.builder().username(username).password(hashedPassword).userRole(cacheService.getUserRoles().get(userRole.name())).build();

        return Optional.of(userRepository.save(user));
    }


    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", username)
        ));

        List<GrantedAuthority> authorities = new ArrayList<>(Collections.emptyList());
        authorities.add((GrantedAuthority) () -> user.getUserRole().getName());

        // Convert our User to which Spring Security understand
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
