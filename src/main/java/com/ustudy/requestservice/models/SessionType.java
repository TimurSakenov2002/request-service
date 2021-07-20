package com.ustudy.requestservice.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "session_type")
public class SessionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
