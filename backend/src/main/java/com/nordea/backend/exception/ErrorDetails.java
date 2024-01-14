package com.nordea.backend.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.util.Date;

@Getter
@Setter
public class ErrorDetails {
    // customizing timestamp serialization format
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;
    private int code;
    private String status;
    private String message;
    private String stackTrace;
    private Object data;

    public ErrorDetails() {
        timestamp = new Date();
    }

    public ErrorDetails(HttpStatus httpStatus, String message) {
        this();

        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
    }

    public ErrorDetails(HttpStatus httpStatus, String message, String stackTrace) {
        this(httpStatus, message);

        this.stackTrace = stackTrace;
    }

    public ErrorDetails(HttpStatus httpStatus, String message, String stackTrace, Object data) {
        this(httpStatus, message, stackTrace);

        this.data = data;
    }
}