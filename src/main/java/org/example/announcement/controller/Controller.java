package org.example.announcement.controller;

import org.example.announcement.dto.AnnouncementRequestDTO;
import org.example.announcement.model.Announcement;
import org.example.announcement.service.AnnouncementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {

    AnnouncementService announcementService;

    public Controller(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PostMapping
    public ResponseEntity<Void> post(@RequestBody AnnouncementRequestDTO request) {
        announcementService.save(request.getDescription());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        announcementService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody AnnouncementRequestDTO request) {
        announcementService.update(id, request.getDescription());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Announcement> getAnnouncement(@PathVariable Long id) {
        return ResponseEntity.ok().body(announcementService.findById(id));
    }
}
