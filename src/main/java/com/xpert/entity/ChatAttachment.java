package com.xpert.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "chat_attachments")
public class ChatAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the parent message
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    private ChatMessage message;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "content_type", length = 100)
    private String contentType;

    // Constructors
    public ChatAttachment() {}

    public ChatAttachment(ChatMessage message, String fileUrl, String fileName, String contentType) {
        this.message = message;
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.contentType = contentType;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ChatMessage getMessage() { return message; }
    public void setMessage(ChatMessage message) { this.message = message; }

    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
}
