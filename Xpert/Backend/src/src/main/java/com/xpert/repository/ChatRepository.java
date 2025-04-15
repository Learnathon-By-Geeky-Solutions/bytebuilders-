package com.xpert.repository;

import com.xpert.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    // Find all chats that a user is a participant of
    List<Chat> findDistinctByParticipants_UserId(Long userId);
}
