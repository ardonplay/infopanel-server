package io.github.ardonplay.infopanel.server.common.exceptionHandler;

import io.github.ardonplay.infopanel.server.common.errorsResponse.ApiErrorInfo;
import io.github.ardonplay.infopanel.server.operations.uploadOperations.exceptions.ResourceNotExistException;
import io.github.ardonplay.infopanel.server.operations.userOperations.services.exceptions.UserAlreadyExistsException;
import io.github.ardonplay.infopanel.server.operations.userOperations.services.exceptions.UserAuthenticationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    ApiErrorInfo
    handleUserAlreadyExistsException(HttpServletRequest req, Exception ex) {
        return new ApiErrorInfo(HttpStatus.BAD_REQUEST.value(), req.getRequestURL(), ex.getMessage());
    }


    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    ApiErrorInfo
    handlePageNotFoundException(HttpServletRequest req, Exception ex) {
        return new ApiErrorInfo(HttpStatus.BAD_REQUEST.value(), req.getRequestURL(), ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseBody
    ApiErrorInfo
    handleUserAlreadyExistException(HttpServletRequest req, Exception ex) {
        return new ApiErrorInfo(HttpStatus.CONFLICT.value(), req.getRequestURL(), ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserAuthenticationException.class)
    @ResponseBody
    ApiErrorInfo
    handleUserAuthenticationException(HttpServletRequest req, Exception ex) {
        return new ApiErrorInfo(HttpStatus.UNAUTHORIZED.value(), req.getRequestURL(), ex.getMessage());
    }
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceNotExistException.class)
    @ResponseBody
    ApiErrorInfo handleResourceNotExistException(HttpServletRequest req, Exception ex) {
        return new ApiErrorInfo(HttpStatus.BAD_REQUEST.value(), req.getRequestURL(), ex.getMessage());
    }
}