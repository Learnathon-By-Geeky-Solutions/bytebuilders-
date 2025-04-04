package com.xpert.entity;

import com.xpert.enums.AgreementStatus;
import com.xpert.enums.AgreementType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;

@Entity
@Table(name = "agreements")
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    @JoinColumn(name = "work_unit_id", nullable = false)
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private AgreementStatus status = AgreementStatus.PENDING;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    // Constructors
    public Agreement() {}

    public Agreement(Users client, Users xpert, WorkUnit workUnit, Location location,
                     AgreementType type, LocalTime totalEstimatedTime, BigDecimal totalPrice,
                     AgreementStatus status) {
        this.client = client;
        this.xpert = xpert;
        this.workUnit = workUnit;
        this.location = location;
        this.type = type;
        this.totalEstimatedTime = totalEstimatedTime;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    // Getters and Setters

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Users getClient() { return client; }
    public void setClient(Users client) { this.client = client; }

    public Users getXpert() { return xpert; }
    public void setXpert(Users xpert) { this.xpert = xpert; }

    public WorkUnit getWorkUnit() { return workUnit; }
    public void setWorkUnit(WorkUnit workUnit) { this.workUnit = workUnit; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public AgreementType getType() { return type; }
    public void setType(AgreementType type) { this.type = type; }

    public LocalTime getTotalEstimatedTime() { return totalEstimatedTime; }
    public void setTotalEstimatedTime(LocalTime totalEstimatedTime) { this.totalEstimatedTime = totalEstimatedTime; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public AgreementStatus getStatus() { return status; }
    public void setStatus(AgreementStatus status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
