package com.xpert.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

import com.xpert.entity.common.BaseAddress;

@Entity
@Table(name = "location")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location extends BaseAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(length = 255)
    private String title;

    @Column(name = "special_instruction", columnDefinition = "TEXT")
    private String specialInstruction;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;
}
