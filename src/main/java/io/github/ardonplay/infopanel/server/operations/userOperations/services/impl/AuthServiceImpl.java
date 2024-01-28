package io.github.ardonplay.infopanel.server.operations.userOperations.services.impl;


import io.github.ardonplay.infopanel.server.operations.userOperations.dtos.AuthRequest;
import io.github.ardonplay.infopanel.server.operations.userOperations.dtos.SignUpRequest;
import io.github.ardonplay.infopanel.server.operations.userOperations.services.AuthService;
import io.github.ardonplay.infopanel.server.operations.userOperations.services.UserService;
import io.github.ardonplay.infopanel.server.operations.userOperations.services.utils.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    @Override
    public ResponseEntity<?> createNewUser(SignUpRequest signUpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> createAuthToken(AuthRequest authRequest) {
        return null;
    }
}
