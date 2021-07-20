package com.ustudy.requestservice.models;

import lombok.Data;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "dep_schedule")
public class DepSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private DayOfWeek startDay;
    private DayOfWeek endDay;

    private LocalTime startTime;
    private LocalTime endTime;

    private LocalTime restTimeStart;
    private LocalTime restTimeEnd;

}
