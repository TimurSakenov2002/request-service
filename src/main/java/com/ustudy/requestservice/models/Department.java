package com.ustudy.requestservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private Long seats;

    private String email;

    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private DepSchedule schedule;



    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "department")
    private List<Auditory> auditoryList;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;

    public void addAuditory(Auditory auditory){
        this.auditoryList.add(auditory);
    }




}
