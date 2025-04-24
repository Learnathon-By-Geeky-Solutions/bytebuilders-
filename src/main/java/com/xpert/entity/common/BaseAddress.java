package com.xpert.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Base class for reusable address fields.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseAddress {

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
}
