package io.github.ardonplay.infopanel.server.operations.userOperations.services;

import io.github.ardonplay.infopanel.server.operations.userOperations.dtos.AuthRequest;
import io.github.ardonplay.infopanel.server.operations.userOperations.dtos.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
    ResponseEntity<?> createNewUser(@RequestBody SignUpRequest signUpRequest);

    ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest);
}
