package org.example.announcement.exceptions;
import org.springframework.http.HttpStatus;

public class AnnouncementAlreadyExistException extends AnnouncementServiceException {
    public AnnouncementAlreadyExistException(String requirements) {
        super(
                "Announcement already exist",
                HttpStatus.BAD_REQUEST,
                "INVALID_DESCRIPTION",
                String.format("Description must: %s", requirements)
        );
    }
}