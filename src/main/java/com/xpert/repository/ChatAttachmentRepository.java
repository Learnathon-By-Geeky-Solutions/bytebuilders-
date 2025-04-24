package com.xpert.repository;

import com.xpert.entity.ChatAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatAttachmentRepository extends JpaRepository<ChatAttachment, Long> {

    //  Find all attachments by message
    List<ChatAttachment> findByMessageId(Long messageId);
}
