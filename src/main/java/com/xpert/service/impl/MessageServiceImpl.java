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

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final  ChatRepository chatRepository;

    private final  ChatMessageRepository chatMessageRepository;

    private final  ChatAttachmentRepository chatAttachmentRepository;

    private final  ModelMapper modelMapper;

    @Override
    public ChatMessageDTO sendMessage(SendMessageRequestDTO dto) {
        Chat chat = chatRepository.findById(dto.getChatId())
                .orElseThrow(() -> new IllegalArgumentException("Chat not found"));

        ChatMessage message = new ChatMessage();
        message.setChat(chat);
        message.setSenderId(dto.getSenderId());
        message.setContent(dto.getContent());
        message.setCreatedAt(LocalDateTime.now());
        message.setStatus(MessageStatus.SENT);

        ChatMessage savedMessage = chatMessageRepository.save(message);

        List<ChatAttachment> savedAttachments = Collections.emptyList();
        if (dto.getAttachmentUrls() != null && !dto.getAttachmentUrls().isEmpty()) {
            savedAttachments = dto.getAttachmentUrls().stream()
                    .map(url -> new ChatAttachment(savedMessage, url, null, null))
                    .collect(Collectors.toList());
            chatAttachmentRepository.saveAll(savedAttachments);
        }

        chat.setLastMessageAt(savedMessage.getCreatedAt());
        chatRepository.save(chat);

        List<ChatAttachmentDTO> attachmentDTOs = savedAttachments.stream()
        	    .map(att -> modelMapper.map(att, ChatAttachmentDTO.class))
        	    .toList();  

        ChatMessageDTO response = modelMapper.map(savedMessage, ChatMessageDTO.class);
        response.setAttachments(attachmentDTOs);
        return response;
    }

    @Override
    public List<ChatMessageDTO> getMessagesByChatId(Long chatId) {
        return chatMessageRepository.findByChatIdOrderByCreatedAtAsc(chatId).stream()
                .map(msg -> {
                    ChatMessageDTO dto = modelMapper.map(msg, ChatMessageDTO.class);

                    List<ChatAttachmentDTO> attachmentDTOs = msg.getAttachments() != null
                            ? msg.getAttachments().stream()
                            .map(att -> modelMapper.map(att, ChatAttachmentDTO.class))
                            .toList()
                            : Collections.emptyList();

                    dto.setAttachments(attachmentDTOs);
                    return dto;
                }) .toList();
    }
}
