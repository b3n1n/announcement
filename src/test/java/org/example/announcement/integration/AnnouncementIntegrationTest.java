package org.example.announcement.integration;

import org.example.announcement.exceptions.EmptyDescriptionException;
import org.example.announcement.model.Announcement;
import org.example.announcement.repository.AnnouncementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AnnouncementIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AnnouncementRepository repository;

    @Test
    public void addAndGetAnnouncement_shouldIncrementViews() throws Exception {
        String description = "Integration Test";
        mvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"" + description + "\"}"))
                .andExpect(status().isOk());

        Announcement saved = repository.findAll().get(0);
        Long id = saved.getId();

        mvc.perform(get("/api/" + id))
                .andExpect(status().isOk());

        Announcement updated = repository.findById(id).orElseThrow();
        Assertions.assertEquals(1, updated.getNumberOfViews());
    }

    @Test
    public void updateAnnouncement_shouldChangeDescription() throws Exception {
        Announcement saved = repository.save(new Announcement("Old Description"));

        mvc.perform(patch("/api/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"New Description\"}"))
                .andExpect(status().isOk());

        Announcement updated = repository.findById(saved.getId()).orElseThrow();
        Assertions.assertEquals("New Description", updated.getDescription());
    }

    @Test
    public void deleteAnnouncement_shouldRemoveIt() throws Exception {
        Announcement saved = repository.save(new Announcement("To be deleted"));

        mvc.perform(delete("/api/" + saved.getId()))
                .andExpect(status().isOk());

        Assertions.assertFalse(repository.findById(saved.getId()).isPresent());
    }

    @Test
    public void createEmptyAnnouncement_shouldThrowException() throws Exception {
        mvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAnnouncementThatExist_shouldThrowException() throws Exception {
        repository.save(new Announcement("Description"));
        mvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"Description\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void getEmptyAnnouncement_shouldThrowException() throws Exception {
        mvc.perform(get("/api/1"))
                .andExpect(status().isNotFound());
        mvc.perform(delete("/api/2"))
                .andExpect(status().isNotFound());
        mvc.perform(patch("/api/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"New Description\"}"))
                .andExpect(status().isNotFound());
    }
}
