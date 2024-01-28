package io.github.ardonplay.infopanel.server.operations.userOperations.dtos;

import io.github.ardonplay.infopanel.server.operations.userOperations.models.enums.UserRole;
import lombok.Data;


@Data
public class SignUpRequest {
    private String username;
    private String password;
    private UserRole userRole;
}