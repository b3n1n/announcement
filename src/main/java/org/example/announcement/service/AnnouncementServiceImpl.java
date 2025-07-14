package org.example.announcement.service;

import lombok.RequiredArgsConstructor;
import org.example.announcement.exceptions.AnnouncementAlreadyExistException;
import org.example.announcement.exceptions.AnnouncementNotFoundException;
import org.example.announcement.exceptions.EmptyDescriptionException;
import org.example.announcement.model.Announcement;
import org.example.announcement.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService{

    private final AnnouncementRepository announcementRepository;

    @Override
    @Transactional
    public void save(String description) throws EmptyDescriptionException, AnnouncementAlreadyExistException {
        if (description == null || description.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        if(announcementRepository.existsByDescription(description)){
            throw new AnnouncementAlreadyExistException("An announcement with this description already exist");
        }
        announcementRepository.save(new Announcement(description));
    }

    @Override
    @Transactional
    public void delete(Long id) throws AnnouncementNotFoundException {
        if(announcementRepository.findById(id).isPresent()) {
            announcementRepository.deleteById(id);
        }
        else {
            throw new AnnouncementNotFoundException(id);
        }
    }

    @Override
    @Transactional
    public void update(Long id, String description) throws AnnouncementNotFoundException{
        if(announcementRepository.findById(id).isPresent()) {
            Announcement announcement = announcementRepository.findById(id).get();
            announcement.setDescription(description);
            announcementRepository.save(announcement);
        } else {
            throw new AnnouncementNotFoundException(id);
        }
    }

    @Override
    @Transactional
    public Announcement findById(Long id) throws AnnouncementNotFoundException{
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new AnnouncementNotFoundException(id));
        announcement.setNumberOfViews(announcement.getNumberOfViews() + 1);
        announcementRepository.save(announcement);
        return announcement;
    }
}
