package com.nordea.backend.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
class CustomControllerAdvice {
    @ExceptionHandler(NullPointerException.class) // exception handled
    public ResponseEntity<ErrorDetails> handleNullPointerExceptions(Exception e) {
        // ... potential custom logic

        HttpStatus status = HttpStatus.NOT_FOUND; // 404

        return new ResponseEntity<>(new ErrorDetails(status, e.getMessage()), status);
    }

    @ExceptionHandler(CountryNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleCountryrNotFoundException(Exception ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        String stackTrace = convertStackTraceToString(ex);
        ErrorDetails errorDetails = new ErrorDetails(status, ex.getMessage(), stackTrace);

        return new ResponseEntity<>(errorDetails, status);

    }
    // fallback method
    @ExceptionHandler(Exception.class) // exception handled
    public ResponseEntity<ErrorDetails> handleExceptions(Exception ex) {
        // ... potential custom logic

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500

        String stackTrace = convertStackTraceToString(ex);

        // specifying the stack trace in case of 500s
        ErrorDetails errorDetails = new ErrorDetails(status, ex.getMessage(), stackTrace);

        return new ResponseEntity<>(errorDetails , status);
    }

    private String convertStackTraceToString(Exception ex) {
        // converting the stack trace to String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
