package com.ustudy.requestservice.dto;

import com.ustudy.requestservice.models.Auditory;
import com.ustudy.requestservice.models.Department;
import com.ustudy.requestservice.models.SessionType;
import com.ustudy.requestservice.models.User;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
public class SessionRequestDto {
    private Long id;

    private String startDate;

    private String endDate;

    private String sessionType;

}
