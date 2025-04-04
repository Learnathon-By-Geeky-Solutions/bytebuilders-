package com.xpert.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(length = 255)
    private String title;

    @Column(nullable = false, length = 50)
    private String country;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(name = "zip_code", nullable = false, length = 10)
    private String zipCode;

    @Column(name = "street_address", nullable = false, columnDefinition = "TEXT")
    private String streetAddress;

    @Column(name = "special_instruction", columnDefinition = "TEXT")
    private String specialInstruction;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    // Constructors
    public Location() {}

    public Location(String title, String country, String city, String state, String zipCode,
                    String streetAddress, String specialInstruction, Boolean isActive) {
        this.title = title;
        this.country = country;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.streetAddress = streetAddress;
        this.specialInstruction = specialInstruction;
        this.isActive = isActive;
    }

    // Getters & Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getStreetAddress() { return streetAddress; }
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }

    public String getSpecialInstruction() { return specialInstruction; }
    public void setSpecialInstruction(String specialInstruction) { this.specialInstruction = specialInstruction; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
