package org.example.announcement.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AnnouncementServiceException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String details;

    public AnnouncementServiceException(String message, HttpStatus httpStatus,
                                        String errorCode, String details) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.details = details;
    }

}