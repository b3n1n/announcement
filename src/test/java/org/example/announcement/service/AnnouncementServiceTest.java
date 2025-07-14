package org.example.announcement.service;

import org.example.announcement.exceptions.AnnouncementAlreadyExistException;
import org.example.announcement.exceptions.EmptyDescriptionException;
import org.example.announcement.model.Announcement;
import org.example.announcement.repository.AnnouncementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AnnouncementServiceTest {

    private AnnouncementRepository repository;
    private AnnouncementService service;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(AnnouncementRepository.class);
        service = new AnnouncementServiceImpl(repository);
    }

    @Test
    public void save_ShouldThrowEmptyDescriptionException_WhenDescriptionIsEmpty() {
        assertThrows(EmptyDescriptionException.class, () -> service.save(""));
    }

    @Test
    public void save_ShouldThrowAnnouncementAlreadyExistException_WhenDuplicateDescription() {
        String description = "Duplicate";
        when(repository.existsByDescription(description)).thenReturn(true);

        AnnouncementAlreadyExistException ex = assertThrows(AnnouncementAlreadyExistException.class,
                () -> service.save(description));
        assertTrue(ex.getMessage().contains("already exist"));
    }

    @Test
    public void save_ShouldSaveAnnouncement_WhenValidDescription() throws Exception {
        String description = "Valid";

        when(repository.existsByDescription(description)).thenReturn(false);
        doAnswer(invocation -> {
            Announcement arg = invocation.getArgument(0);
            assertEquals(description, arg.getDescription());
            return null;
        }).when(repository).save(any(Announcement.class));

        service.save(description);

        verify(repository, times(1)).save(any(Announcement.class));
    }

    @Test
    public void findById_ShouldIncrementNumberOfViews() {
        Announcement announcement = new Announcement("Test");
        announcement.setNumberOfViews(0);
        announcement.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(announcement));
        when(repository.save(any(Announcement.class))).thenReturn(announcement);

        Announcement found = service.findById(1L);

        assertEquals(1, found.getNumberOfViews());
        verify(repository).save(announcement);
    }
}
