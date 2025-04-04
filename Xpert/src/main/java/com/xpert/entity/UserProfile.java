package com.xpert.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userId;  // References users.id

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private Users user;  // Assuming a User entity exists

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "experiences_in_short", columnDefinition = "TEXT")
    private String experiencesInShort;

    @Column(columnDefinition = "TEXT") // Can be changed if file handling is different
    private String cv;

    @Column(name = "completed_works")
    private Integer completedWorks;

    @ElementCollection
    @CollectionTable(name = "user_notifications", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "notification", columnDefinition = "TEXT")
    private List<String> notifications;  // Storing as a text array equivalent

    // Constructors
    public UserProfile() {
    }

    public UserProfile(UUID userId, Users user, String image, String description, String experiencesInShort, String cv, Integer completedWorks, List<String> notifications) {
        this.userId = userId;
        this.user = user;
        this.image = image;
        this.description = description;
        this.experiencesInShort = experiencesInShort;
        this.cv = cv;
        this.completedWorks = completedWorks;
        this.notifications = notifications;
    }

    // Getters and Setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExperiencesInShort() {
        return experiencesInShort;
    }

    public void setExperiencesInShort(String experiencesInShort) {
        this.experiencesInShort = experiencesInShort;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public Integer getCompletedWorks() {
        return completedWorks;
    }

    public void setCompletedWorks(Integer completedWorks) {
        this.completedWorks = completedWorks;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }
}
