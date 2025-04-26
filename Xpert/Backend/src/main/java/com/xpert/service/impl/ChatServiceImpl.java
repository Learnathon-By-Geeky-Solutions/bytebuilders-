package com.xpert.service.impl;

import com.xpert.dto.ChatDTO;
import com.xpert.dto.CreateChatRequestDTO;
import com.xpert.entity.chat.Chat;
import com.xpert.entity.chat.ChatParticipant;
import com.xpert.repository.ChatParticipantRepository;
import com.xpert.repository.ChatRepository;
import com.xpert.service.ChatService;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

 
    private final ChatRepository chatRepository;

   
    private final ChatParticipantRepository chatParticipantRepository;

 
    private final  ModelMapper modelMapper;

    @Override
    public ChatDTO createChat(CreateChatRequestDTO dto) {
        Chat chat = modelMapper.map(dto, Chat.class);
        chat.setCreatedAt(LocalDateTime.now());

        Chat savedChat = chatRepository.save(chat);

        List<ChatParticipant> participants = dto.getParticipantIds().stream()
                .map(userId -> new ChatParticipant(savedChat, userId))
                .collect(Collectors.toList());

        chatParticipantRepository.saveAll(participants);

        ChatDTO response = modelMapper.map(savedChat, ChatDTO.class);
        response.setParticipantIds(dto.getParticipantIds());
        return response;
    }

    @Override
    public List<ChatDTO> getChatsForUser(Long userId) {
        return chatRepository.findDistinctByParticipants_UserId(userId).stream()
            .map(chat -> {
                ChatDTO dto = modelMapper.map(chat, ChatDTO.class);
                dto.setParticipantIds(
                    chat.getParticipants().stream()
                        .map(ChatParticipant::getUserId)
                        .toList() 
                );
                return dto;
            })
            .toList(); 
    }

}
