package com.xpert.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

import com.xpert.entity.common.BaseAddress;

@Entity
@Table(name = "user_addresses")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddress extends BaseAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 255)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}
