package com.xpert.entity;

import com.xpert.converter.EncryptedStringConverter;
import com.xpert.enums.PaymentMethodType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payment_methods")
@Getter
@Setter
public class PaymentMethod {

	@Id
	@Column(name = "id", nullable = false, columnDefinition = "BINARY(16)")
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PaymentMethodType type;

    @Column(nullable = false, length = 50)
    private String provider;

    @Convert(converter = EncryptedStringConverter.class) // ✅ encryption here
    @Column(name = "account_no", nullable = false, length = 50)
    private String accountNo;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    // Constructors
    public PaymentMethod() {}

    public PaymentMethod(Users user, PaymentMethodType type, String provider,
                         String accountNo, Boolean isDefault) {
        this.user = user;
        this.type = type;
        this.provider = provider;
        this.accountNo = accountNo;
        this.isDefault = isDefault;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

   
}
