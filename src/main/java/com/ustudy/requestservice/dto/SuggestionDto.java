package com.ustudy.requestservice.dto;

import lombok.Data;

@Data
public class SuggestionDto {

    private Long id;

    private String reason;

    private String startDate;

    private String endDate;

    private Boolean accepted;

    private Long auditory;

    private Long requestResult;
}
