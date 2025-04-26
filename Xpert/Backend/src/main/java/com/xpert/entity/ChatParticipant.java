package com.xpert.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(
    name = "chat_participants",
    uniqueConstraints = @UniqueConstraint(columnNames = {"chat_id", "user_id"})
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ChatParticipant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;


    // Many participants belong to one chat
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    @JsonIgnore
    private Chat chat;


    // Reference to the User entity (assuming UUID as user ID)
    @Column(name = "user_id", nullable = false)
    private Long userId;


    public ChatParticipant(Chat chat, Long userId) {
        this.chat = chat;
        this.userId = userId;
    }

   
}
