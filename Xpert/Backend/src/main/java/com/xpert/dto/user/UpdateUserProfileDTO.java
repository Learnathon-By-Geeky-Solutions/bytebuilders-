package com.xpert.dto.user;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserProfileDTO {

    private String image;

    @Size(max = 5000, message = "Description too long")
    private String description;

    @Size(max = 3000, message = "Experiences in short is too long")
    private String experiencesInShort;

    private String cv;

    private Integer completedWorks;
}
