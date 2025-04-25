package com.xpert.service.impl;

import com.xpert.dto.ChatDTO;
import com.xpert.dto.CreateChatRequestDTO;
import com.xpert.entity.Chat;
import com.xpert.entity.ChatParticipant;
import com.xpert.repository.ChatParticipantRepository;
import com.xpert.repository.ChatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceImplTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private ChatParticipantRepository chatParticipantRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ChatServiceImpl chatService;

    private Chat chat;
    private ChatDTO chatDTO;
    private CreateChatRequestDTO createRequest;

    @BeforeEach
    void setUp() {
        createRequest = CreateChatRequestDTO.builder()
                .title("Test Chat")
                .participantIds(List.of(1L, 2L))
                .build();

        chat = new Chat();
        chat.setId(10L);
        chat.setTitle("Test Chat");
        chat.setCreatedAt(LocalDateTime.now());

        chatDTO = new ChatDTO();
        chatDTO.setChatId(10L);
        chatDTO.setTitle("Test Chat");
        chatDTO.setParticipantIds(List.of(1L, 2L));
    }

    @Test
    void shouldCreateChatSuccessfully() {
        given(modelMapper.map(createRequest, Chat.class)).willReturn(chat);
        given(chatRepository.save(any(Chat.class))).willReturn(chat);
        given(modelMapper.map(chat, ChatDTO.class)).willReturn(chatDTO);

        ChatDTO result = chatService.createChat(createRequest);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Chat");
        verify(chatParticipantRepository).saveAll(anyList());
    }

    @Test
    void shouldReturnChatsForUser() {
        ChatParticipant p1 = new ChatParticipant(chat, 1L);
        ChatParticipant p2 = new ChatParticipant(chat, 2L);
        chat.setParticipants(List.of(p1, p2));

        given(chatRepository.findDistinctByParticipants_UserId(1L)).willReturn(List.of(chat));
        given(modelMapper.map(chat, ChatDTO.class)).willReturn(chatDTO);

        List<ChatDTO> chats = chatService.getChatsForUser(1L);

        assertThat(chats).hasSize(1);
        assertThat(chats.get(0).getParticipantIds()).containsExactly(1L, 2L);
    }
}