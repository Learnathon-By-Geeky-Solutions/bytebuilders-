package com.xpert.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * Entity representing extended profile information for a user.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // ✅ Equality based only on userId
@Entity
@Table(name = "user_profile")
public class UserProfile {

    /**
     * Primary key that maps directly to the user ID.
     */
    @Id
    @EqualsAndHashCode.Include // ✅ Include this field in equals/hashCode
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    /**
     * One-to-one relationship with the user entity.
     * This also acts as the foreign key and primary key due to @MapsId.
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private Users user;

    /**
     * Profile image (URL or base64 or path depending on implementation).
     */
    @Column(columnDefinition = "TEXT")
    private String image;

    /**
     * Short bio or about section.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Work history or summary of experiences.
     */
    @Column(name = "experiences_in_short", columnDefinition = "TEXT")
    private String experiencesInShort;

    /**
     * File path or text data for CV/resume.
     */
    @Column(columnDefinition = "TEXT")
    private String cv;

    /**
     * Number of completed jobs or contracts.
     */
    @Column(name = "completed_works")
    private Integer completedWorks;

    /**
     * Notification list (e.g., messages, alerts, updates).
     */
    @ElementCollection
    @CollectionTable(name = "user_notifications", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "notification", columnDefinition = "TEXT")
    private List<String> notifications;
}
