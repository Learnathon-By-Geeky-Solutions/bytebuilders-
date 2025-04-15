package com.xpert.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "chat_participants",
    uniqueConstraints = @UniqueConstraint(columnNames = {"chat_id", "user_id"})
)
public class ChatParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many participants belong to one chat
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    // Reference to the User entity (assuming UUID as user ID)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // Constructors
    public ChatParticipant() {}

    public ChatParticipant(Chat chat, Long userId) {
        this.chat = chat;
        this.userId = userId;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Chat getChat() { return chat; }
    public void setChat(Chat chat) { this.chat = chat; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
