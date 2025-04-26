package com.xpert.service.impl;

import com.xpert.dto.ChatAttachmentDTO;
import com.xpert.dto.ChatMessageDTO;
import com.xpert.dto.SendMessageRequestDTO;
import com.xpert.entity.chat.Chat;
import com.xpert.entity.ChatAttachment;
import com.xpert.entity.chat.ChatMessage;
import com.xpert.enums.MessageStatus;
import com.xpert.repository.ChatAttachmentRepository;
import com.xpert.repository.ChatMessageRepository;
import com.xpert.repository.ChatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock private ChatRepository chatRepository;
    @Mock private ChatMessageRepository chatMessageRepository;
    @Mock private ChatAttachmentRepository chatAttachmentRepository;
    @Mock private ModelMapper modelMapper;

    @InjectMocks
    private MessageServiceImpl messageService;

    private Chat chat;
    private ChatMessage message;
    private ChatMessageDTO messageDTO;
    private SendMessageRequestDTO request;

    @BeforeEach
    void setUp() {
        chat = new Chat();
        chat.setId(1L);

        message = new ChatMessage();
        message.setId(1L);
        message.setChat(chat);
        message.setSenderId(10L);
        message.setContent("Hello");
        message.setStatus(MessageStatus.SENT);
        message.setCreatedAt(LocalDateTime.now());

        messageDTO = new ChatMessageDTO();
        messageDTO.setMessageId(1L);
        messageDTO.setContent("Hello");

        request = SendMessageRequestDTO.builder()
                .chatId(1L)
                .senderId(10L)
                .content("Hello")
                .attachmentUrls(List.of("http://image.png"))
                .build();
    }

    @Test
    void shouldSendMessageWithAttachmentsSuccessfully() {
        given(chatRepository.findById(1L)).willReturn(Optional.of(chat));
        given(chatMessageRepository.save(any())).willReturn(message);
        given(chatAttachmentRepository.saveAll(any())).willReturn(List.of(new ChatAttachment()));
        given(modelMapper.map(any(ChatAttachment.class), eq(ChatAttachmentDTO.class)))
                .willReturn(new ChatAttachmentDTO());
        given(modelMapper.map(any(ChatMessage.class), eq(ChatMessageDTO.class)))
                .willReturn(messageDTO);

        ChatMessageDTO result = messageService.sendMessage(request);

        assertThat(result).isNotNull();
        verify(chatRepository).save(chat);
    }

    @Test
    void shouldSendMessageWithoutAttachments() {
        request.setAttachmentUrls(Collections.emptyList());

        given(chatRepository.findById(1L)).willReturn(Optional.of(chat));
        given(chatMessageRepository.save(any())).willReturn(message);
        given(modelMapper.map(any(ChatMessage.class), eq(ChatMessageDTO.class)))
                .willReturn(messageDTO);

        ChatMessageDTO result = messageService.sendMessage(request);

        assertThat(result).isNotNull();
        verify(chatAttachmentRepository, never()).saveAll(any());
    }

    @Test
    void shouldThrowIfChatNotFound() {
        given(chatRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> messageService.sendMessage(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Chat not found");
    }

    @Test
    void shouldReturnMessagesByChatId() {
        ChatAttachment attachment = new ChatAttachment();
        message.setAttachments(List.of(attachment));

        given(chatMessageRepository.findByChatIdOrderByCreatedAtAsc(1L)).willReturn(List.of(message));
        given(modelMapper.map(any(ChatMessage.class), eq(ChatMessageDTO.class))).willReturn(messageDTO);
        given(modelMapper.map(any(ChatAttachment.class), eq(ChatAttachmentDTO.class))).willReturn(new ChatAttachmentDTO());

        List<ChatMessageDTO> result = messageService.getMessagesByChatId(1L);

        assertThat(result).hasSize(1);
        verify(chatMessageRepository).findByChatIdOrderByCreatedAtAsc(1L);
    }

    @Test
    void shouldReturnEmptyAttachmentsIfNoneExist() {
        message.setAttachments(null);  // simulate no attachments

        given(chatMessageRepository.findByChatIdOrderByCreatedAtAsc(1L)).willReturn(List.of(message));
        given(modelMapper.map(any(ChatMessage.class), eq(ChatMessageDTO.class))).willReturn(messageDTO);

        List<ChatMessageDTO> result = messageService.getMessagesByChatId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAttachments()).isEmpty();  //  FIXED
    }
    
    @Test
    void shouldSendMessageWhenAttachmentsAreNull() {
        // Simulate null attachments
        request.setAttachmentUrls(null);

        given(chatRepository.findById(1L)).willReturn(Optional.of(chat));
        given(chatMessageRepository.save(any())).willReturn(message);
        given(modelMapper.map(any(ChatMessage.class), eq(ChatMessageDTO.class)))
                .willReturn(messageDTO);

        ChatMessageDTO result = messageService.sendMessage(request);

        assertThat(result).isNotNull();
        verify(chatAttachmentRepository, never()).saveAll(any()); // no attachments saved
    }

}
