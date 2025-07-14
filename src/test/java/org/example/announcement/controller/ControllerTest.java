package org.example.announcement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.announcement.dto.AnnouncementRequestDTO;
import org.example.announcement.model.Announcement;
import org.example.announcement.service.AnnouncementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Controller.class)
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private AnnouncementService announcementService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void postAnnouncement() throws Exception {
        AnnouncementRequestDTO announcement = new AnnouncementRequestDTO("Test Announcement");

        mvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(announcement)))
                .andExpect(status().isOk());
        Mockito.verify(announcementService).save(announcement.getDescription());
    }

    @Test
    public void deleteAnnouncement_Success() throws Exception {
        Long id = 1L;

        mvc.perform(delete("/api/{id}", id))
                .andExpect(status().isOk());

        Mockito.verify(announcementService).delete(id);
    }

    @Test
    public void patchAnnouncement_Success() throws Exception {
        Long id = 1L;
        String requestBody = "{\"description\":\"Test Announcement\"}";
        Announcement expectedAnnouncement = new Announcement();
        expectedAnnouncement.setDescription("Test Announcement");

        mvc.perform(patch("/api/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        Mockito.verify(announcementService).update(eq(id), argThat(argument ->
                argument.equals("Test Announcement")
        ));
    }

    @Test
    public void getAnnouncement_Success_AndIncrementsNumberOfViews() throws Exception {
        Long id = 1L;
        Announcement mockAnnouncement = new Announcement("Test Announcement");
        mockAnnouncement.setId(id);

        Mockito.when(announcementService.findById(id)).thenAnswer(invocation -> {
            mockAnnouncement.setNumberOfViews(mockAnnouncement.getNumberOfViews() + 1);
            return mockAnnouncement;
        });


        mvc.perform(get("/api/{id}", id))
                .andExpect(status().isOk());

        Mockito.verify(announcementService).findById(id);

        Assertions.assertEquals(1, mockAnnouncement.getNumberOfViews());
    }
}
