package com.xpert.service.impl;

import com.xpert.dto.ChatDTO;
import com.xpert.dto.CreateChatRequestDTO;
import com.xpert.entity.Chat;
import com.xpert.entity.ChatParticipant;
import com.xpert.enums.ChatType;
import com.xpert.repository.ChatParticipantRepository;
import com.xpert.repository.ChatRepository;
import com.xpert.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatParticipantRepository chatParticipantRepository;

    @Override
    public ChatDTO createChat(CreateChatRequestDTO dto) {
        // Create and save Chat entity
        Chat chat = new Chat();
        chat.setType(dto.getType());
        chat.setTitle(dto.getTitle());
        chat.setOrderId(dto.getOrderId());
        chat.setCreatedAt(LocalDateTime.now());

        Chat savedChat = chatRepository.save(chat);

        // Create participants
        List<ChatParticipant> participants = dto.getParticipantIds().stream()
                .map(userId -> new ChatParticipant(savedChat, userId))
                .collect(Collectors.toList());

        chatParticipantRepository.saveAll(participants);

        // Return ChatDTO
        return new ChatDTO(
                savedChat.getId(),
                savedChat.getType(),
                savedChat.getTitle(),
                savedChat.getOrderId(),
                dto.getParticipantIds(),
                savedChat.getLastMessageAt()
        );
    }

    @Override
    public List<ChatDTO> getChatsForUser(Long userId) {
        return chatRepository.findDistinctByParticipants_UserId(userId).stream()
                .map(chat -> new ChatDTO(
                        chat.getId(),
                        chat.getType(),
                        chat.getTitle(),
                        chat.getOrderId(),
                        chat.getParticipants().stream()
                                .map(ChatParticipant::getUserId)
                                .collect(Collectors.toList()),
                        chat.getLastMessageAt()
                )).collect(Collectors.toList());
    }
}
