package com.xpert.dto.address;

import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserAddressDTO {
    private UUID id;
    private String title;
    private String country;
    private String city;
    private String state;
    private String zipCode;
    private String streetAddress;
    private Boolean isDefault;

}
