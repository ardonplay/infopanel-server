package io.github.ardonplay.infopanel.server.operations.userOperations.services.exceptions;

public class UserAuthenticationException extends RuntimeException {
    public UserAuthenticationException(String message) {
        super(message);
    }
}
