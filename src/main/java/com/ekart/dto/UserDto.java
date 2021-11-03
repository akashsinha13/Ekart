package com.ekart.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String email;
    private boolean isAdmin;
    private LocalDate lastLogin;
    private LocalDate createdDate;
    private Long primaryMobile;
}
