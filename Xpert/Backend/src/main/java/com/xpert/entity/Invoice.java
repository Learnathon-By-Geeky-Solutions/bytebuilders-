package com.xpert.entity;

import com.xpert.converter.EncryptedStringConverter;
import com.xpert.enums.InvoiceStatus;
import com.xpert.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entity representing an invoice for a service order.
 * Invoices store billing details, references, and financial status
 * for a completed or scheduled order.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "invoices")
@EntityListeners(AuditingEntityListener.class)

public class Invoice {

    /**
     * Primary key (UUID) with binary column mapping for performance and security.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    /**
     * Associated order (many invoices may eventually be linked to one order).
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /**
     * Unique encrypted reference ID for external systems or clients.
     */
    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "reference_id", length = 50)
    private String referenceId;

    /**
     * Description of the invoice, such as services rendered or additional notes.
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * When the associated work or service is scheduled.
     */
    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    /**
     * When the associated work or service was completed.
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    /**
     * Status of the invoice (e.g., GENERATED, SENT, CANCELLED).
     * Uses @Builder.Default to ensure proper initialization during object creation.
     */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InvoiceStatus status = InvoiceStatus.GENERATED;

    /**
     * Total amount billed in the invoice.
     */
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    /**
     * Payment status (e.g., PAID, UNPAID, PENDING).
     * Uses @Builder.Default for consistent DTO-to-entity construction.
     */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 25)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    /**
     * When the invoice was officially issued (set at creation).
     */
    @Builder.Default
    @Column(name = "issued_at", nullable = false)
    private Instant issuedAt = Instant.now();

    /**
     * Timestamp of invoice record creation.
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Timestamp of the last update to this invoice.
     */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
