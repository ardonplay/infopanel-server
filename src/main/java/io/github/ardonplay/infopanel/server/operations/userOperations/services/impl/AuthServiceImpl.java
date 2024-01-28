package io.github.ardonplay.infopanel.server.operations.userOperations.services.impl;


import io.github.ardonplay.infopanel.server.common.entities.UserEntity;
import io.github.ardonplay.infopanel.server.operations.userOperations.dtos.AuthRequest;
import io.github.ardonplay.infopanel.server.operations.userOperations.dtos.JwtResponse;
import io.github.ardonplay.infopanel.server.operations.userOperations.dtos.SignUpRequest;
import io.github.ardonplay.infopanel.server.operations.userOperations.services.AuthService;
import io.github.ardonplay.infopanel.server.operations.userOperations.services.UserService;
import io.github.ardonplay.infopanel.server.operations.userOperations.services.exceptions.UserAlreadyExistsException;
import io.github.ardonplay.infopanel.server.operations.userOperations.services.exceptions.UserAuthenticationException;
import io.github.ardonplay.infopanel.server.operations.userOperations.services.utils.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Service
@Slf4j
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    @Override
    public ResponseEntity<?> createNewUser(SignUpRequest signUpRequest) {
        var username = signUpRequest.getUsername();

        if (userService.findByUsername(username).isPresent()) {
            var msg = String.format("Username '%s' is already taken", username);
            log.info(msg);
            throw new UserAlreadyExistsException(msg);
        }

        try {
            var user = userService
                    .createUser(username, signUpRequest.getPassword(), signUpRequest.getUserRole())
                    .orElseThrow();
            return ResponseEntity.ok(user);

        } catch (NoSuchElementException ex) {
            log.error(Arrays.toString(ex.getStackTrace()));
            return ResponseEntity.status(500).build();
        }
    }

    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(), authRequest.getPassword()
                    ));
            // желательно обработать!
            UserEntity user = userService.findByUsername(authRequest.getUsername()).orElseThrow();
            String token = jwtTokenProvider.generateToken(user);
            log.info("Token: {}", token);
            return ResponseEntity.ok(new JwtResponse(token));

        } catch (BadCredentialsException ex) {
            log.info(ex.getMessage());
            throw new UserAuthenticationException("Wrong username or password");
        }

    }
}
