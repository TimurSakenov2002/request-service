package com.ustudy.requestservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ustudy.requestservice.components.StringToDate;
import com.ustudy.requestservice.dto.SessionRequestDto;
import lombok.Data;

import javax.persistence.*;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class SessionRequest implements Comparable<SessionRequest> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startDate;

    private Date endDate;

    private Date uploadDate = new Date();


    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "auditory_id", referencedColumnName = "id")
    private Auditory auditory;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private SessionType sessionType;


    @JsonBackReference
    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "result_id", referencedColumnName = "id")
    private RequestResult requestResult;

    public SessionRequest(SessionRequestDto sessionRequestDto) throws ParseException {
        setStartDate(StringToDate.stringToDateTime(sessionRequestDto.getStartDate()));
        setEndDate(StringToDate.stringToDateTime(sessionRequestDto.getEndDate()));
    }

    public SessionRequest() {

    }

    @Override
    public int compareTo(SessionRequest o) {
        int startTime = o.getStartDate().getHours();
        return this.startDate.getHours() - startTime;
    }
}
