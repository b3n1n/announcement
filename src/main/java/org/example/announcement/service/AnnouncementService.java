package org.example.announcement.service;

import org.example.announcement.exceptions.AnnouncementAlreadyExistException;
import org.example.announcement.exceptions.AnnouncementNotFoundException;
import org.example.announcement.exceptions.EmptyDescriptionException;
import org.example.announcement.model.Announcement;

public interface AnnouncementService {
    void save(String description) throws EmptyDescriptionException, AnnouncementAlreadyExistException;
    void delete(Long id) throws AnnouncementNotFoundException;
    void update(Long id, String description) throws AnnouncementNotFoundException;
    Announcement findById(Long id) throws AnnouncementNotFoundException;
}
