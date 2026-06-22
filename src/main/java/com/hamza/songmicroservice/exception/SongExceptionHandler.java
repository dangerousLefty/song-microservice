package com.hamza.songmicroservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SongExceptionHandler {
    @ExceptionHandler(SongIdentifierExistsException.class)
    public ResponseEntity<ErrorResponse> handleSongIdentifierExists(
            SongIdentifierExistsException e
    ) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(e.getMessage()));
    }

    public record ErrorResponse(String message) {}
}
