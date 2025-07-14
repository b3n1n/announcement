package org.example.announcement.exceptions;
import org.springframework.http.HttpStatus;

public class EmptyDescriptionException extends AnnouncementServiceException {
    public EmptyDescriptionException() {
        super(
                "Description cannot be empty",
                HttpStatus.BAD_REQUEST,
                "INVALID_DESCRIPTION",
                "Description must: meaningful contest"
        );
    }
}