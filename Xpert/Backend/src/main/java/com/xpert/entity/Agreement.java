package com.xpert.entity;

import com.xpert.enums.AgreementStatus;
import com.xpert.enums.AgreementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agreements")
public class Agreement {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    // FK → users.id (client)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Users client;

    // FK → users.id (xpert)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "xpert_id", nullable = false)
    private Users xpert;

    // FK → work_unit.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_unit_id", nullable = false, columnDefinition = "BINARY(16)")
    private WorkUnit workUnit;


    // FK → location.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 100)
    private AgreementType type;

    @Column(name = "total_estimated_time")
    private LocalTime totalEstimatedTime;

    @Column(name = "total_price", precision = 12, scale = 2)
    private BigDecimal totalPrice;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private AgreementStatus status = AgreementStatus.PENDING;


    @Builder.Default
    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();


    @Builder.Default
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();
}
