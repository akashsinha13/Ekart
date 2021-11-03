package com.ekart.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
public class Address {
    private AddressType addressType;

    private boolean isPrimary;

    private String street;

    private String city;

    private String state;

    private String country;

    private Long zipCode;

    enum AddressType {
        BILLING, SHIPPING;
    }
}
