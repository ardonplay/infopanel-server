package io.github.ardonplay.infopanel.server.operations.uploadOperations.exceptions;

public class ResourceAlreadyExistException extends ResourceException{
    public ResourceAlreadyExistException() {
    }

    public ResourceAlreadyExistException(String message) {
        super(message);
    }

    public ResourceAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
