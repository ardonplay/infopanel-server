package io.github.ardonplay.infopanel.server.operations.userOperations.dtos;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
