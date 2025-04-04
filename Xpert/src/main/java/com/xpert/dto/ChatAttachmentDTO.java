package com.xpert.dto;

public class ChatAttachmentDTO {

    private String fileUrl;
    private String fileName;
    private String contentType;

    // Constructors
    public ChatAttachmentDTO() {}

    public ChatAttachmentDTO(String fileUrl, String fileName, String contentType) {
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.contentType = contentType;
    }

    // Getters and Setters
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
