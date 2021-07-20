package com.ustudy.requestservice.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "admin_id",referencedColumnName = "id")
    private UserDetail userDetail;


    @OneToOne
    @JoinColumn(name ="province_id",referencedColumnName = "id")
    private Province province;
}
