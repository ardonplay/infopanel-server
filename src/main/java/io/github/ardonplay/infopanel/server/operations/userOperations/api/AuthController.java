package io.github.ardonplay.infopanel.server.operations.userOperations.api;


import io.github.ardonplay.infopanel.server.operations.userOperations.dtos.AuthRequest;
import io.github.ardonplay.infopanel.server.operations.userOperations.dtos.SignUpRequest;
import io.github.ardonplay.infopanel.server.operations.userOperations.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST})
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> createNewUser(@RequestBody SignUpRequest signUpRequest) {
        return authService.createNewUser(signUpRequest);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }


}
