package com.ustudy.requestservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class RequestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "boolean default false")
    private Boolean isSeen=false;
    private Boolean accepted;

    @JsonManagedReference
    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "session_id",referencedColumnName = "id")
    private SessionRequest sessionRequest;

    @JsonManagedReference
    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "suggestion_id",referencedColumnName = "id")
    private Suggestion suggestion;




}
