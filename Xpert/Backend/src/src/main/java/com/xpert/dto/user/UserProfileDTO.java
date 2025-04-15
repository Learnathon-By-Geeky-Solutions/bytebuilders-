package com.xpert.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private UUID userId;
    private String image;
    private String description;
    private String experiencesInShort;
    private String cv;
    private Integer completedWorks;
    private List<String> notifications;
}
