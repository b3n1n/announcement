package org.example.announcement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnnouncementRequestDTO {
    private String description;

    public AnnouncementRequestDTO(String description) {
        this.description = description;
    }
}
