package com.ustudy.requestservice.dto;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class RegistrationDto {
    private String email;

    private String password;

    private String name;

    private String surname;

    private String birthdate;

    private String phoneNumber;


}
