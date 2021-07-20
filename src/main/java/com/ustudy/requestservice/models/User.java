package com.ustudy.requestservice.models;

import com.ustudy.requestservice.components.StringToDate;
import com.ustudy.requestservice.dto.RegistrationDto;
import lombok.Data;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private Date birthdate;

    private String phoneNumber;


    @OneToOne
    @JoinColumn(name = "user_detail",referencedColumnName = "id")
    private UserDetail userDetail;


    public User(RegistrationDto registrationDto) throws ParseException {
        setName(registrationDto.getName());
        setSurname(registrationDto.getSurname());
        Date date = StringToDate.stringToDate(registrationDto.getBirthdate());
        setBirthdate(date);
        setPhoneNumber(registrationDto.getPhoneNumber());
    }

    public User() {

    }


}
