package com.ustudy.requestservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reason;

    private Date startDate;

    private Date endDate;

    private Date uploadDate = new Date();

    @Column(columnDefinition = "boolean default false")
    private Boolean accepted;

    @ManyToOne
    @JoinColumn(name = "auditory_id", referencedColumnName = "id")
    private Auditory auditory;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "result_id", referencedColumnName = "id")
    private RequestResult requestResult;
}
