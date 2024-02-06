package io.github.ardonplay.infopanel.server.operations.uploadOperations.exceptions;

public class ResourceNotExistException extends ResourceException{

    public ResourceNotExistException() {
    }

    public ResourceNotExistException(String message) {
        super(message);
    }

    public ResourceNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotExistException(Throwable cause) {
        super(cause);
    }
}
