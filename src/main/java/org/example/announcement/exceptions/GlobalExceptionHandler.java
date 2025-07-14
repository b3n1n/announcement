package org.example.announcement.exceptions;

import org.example.announcement.model.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AnnouncementNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleAnnouncementNotFound(
            AnnouncementNotFoundException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({EmptyDescriptionException.class})
    public ResponseEntity<ApiErrorResponse> handleBadRequestExceptions(
            EmptyDescriptionException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({AnnouncementAlreadyExistException.class})
    public ResponseEntity<ApiErrorResponse> handleAnnouncementAlreadyExists(
            AnnouncementAlreadyExistException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.CONFLICT, request);
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(
            Exception ex, HttpStatus status, WebRequest request) {
        ApiErrorResponse response = new ApiErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(response, status);
    }
}