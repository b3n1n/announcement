package org.example.announcement.exceptions;

import org.springframework.http.HttpStatus;

public class AnnouncementNotFoundException extends AnnouncementServiceException {
    public AnnouncementNotFoundException(Long id) {
        super(
                "Announcement not found",
                HttpStatus.NOT_FOUND,
                "ANNOUNCEMENT_NOT_FOUND",
                String.format("Announcement with ID %d does not exist", id)
        );
    }
}