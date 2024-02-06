package io.github.ardonplay.infopanel.server.common.errorsResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiErrorInfo {
    private LocalDateTime timestamp;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private StringBuffer url;
    private int status;
    private String message;

    private ApiErrorInfo() {
        timestamp = LocalDateTime.now();
    }

    public ApiErrorInfo(int status, StringBuffer url, String message) {
        this();
        this.status = status;
        this.url = url;
        this.message = message;

    }
}