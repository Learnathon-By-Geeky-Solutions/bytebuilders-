package com.xpert.service.impl;

import com.xpert.dto.ChatAttachmentDTO;
import com.xpert.dto.ChatMessageDTO;
import com.xpert.dto.SendMessageRequestDTO;
import com.xpert.entity.Chat;
import com.xpert.entity.ChatAttachment;
import com.xpert.entity.ChatMessage;
import com.xpert.enums.MessageStatus;
import com.xpert.repository.ChatAttachmentRepository;
import com.xpert.repository.ChatMessageRepository;
import com.xpert.repository.ChatRepository;
import com.xpert.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatAttachmentRepository chatAttachmentRepository;

    @Override
    public ChatMessageDTO sendMessage(SendMessageRequestDTO dto) {
        Chat chat = chatRepository.findById(dto.getChatId())
                .orElseThrow(() -> new IllegalArgumentException("Chat not found"));

        // Create and save message
        ChatMessage message = new ChatMessage();
        message.setChat(chat);
        message.setSenderId(dto.getSenderId());
        message.setContent(dto.getContent());
        message.setCreatedAt(LocalDateTime.now());
        message.setStatus(MessageStatus.SENT);

        ChatMessage savedMessage = chatMessageRepository.save(message);

        // Save attachments (if any)
        List<ChatAttachment> savedAttachments = Collections.emptyList();
        if (dto.getAttachmentUrls() != null && !dto.getAttachmentUrls().isEmpty()) {
            savedAttachments = dto.getAttachmentUrls().stream()
                    .map(url -> new ChatAttachment(savedMessage, url, null, null))
                    .collect(Collectors.toList());
            chatAttachmentRepository.saveAll(savedAttachments);
        }

        // Update chat's last message timestamp
        chat.setLastMessageAt(savedMessage.getCreatedAt());
        chatRepository.save(chat);

        // Return DTO
        List<ChatAttachmentDTO> attachmentDTOs = savedAttachments.stream()
                .map(att -> new ChatAttachmentDTO(att.getFileUrl(), att.getFileName(), att.getContentType()))
                .collect(Collectors.toList());

        return new ChatMessageDTO(
                savedMessage.getId(),
                chat.getId(),
                savedMessage.getSenderId(),
                savedMessage.getContent(),
                savedMessage.getCreatedAt(),
                savedMessage.getStatus(),
                attachmentDTOs
        );
    }

    @Override
    public List<ChatMessageDTO> getMessagesByChatId(Long chatId) {
        return chatMessageRepository.findByChatIdOrderByCreatedAtAsc(chatId).stream()
                .map(msg -> {
                    List<ChatAttachmentDTO> attachmentDTOs = msg.getAttachments() != null
                            ? msg.getAttachments().stream()
                                .map(att -> new ChatAttachmentDTO(att.getFileUrl(), att.getFileName(), att.getContentType()))
                                .collect(Collectors.toList())
                            : Collections.emptyList();

                    return new ChatMessageDTO(
                            msg.getId(),
                            msg.getChat().getId(),
                            msg.getSenderId(),
                            msg.getContent(),
                            msg.getCreatedAt(),
                            msg.getStatus(),
                            attachmentDTOs
                    );
                }).collect(Collectors.toList());
    }
}
