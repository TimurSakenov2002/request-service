package com.ustudy.requestservice.dto;

import com.ustudy.requestservice.models.RequestResult;
import com.ustudy.requestservice.models.SessionRequest;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RequestResultsWithConflicts {
    private List<SessionRequest> sessionRequests;
    private Map<Long, Long> sessionConflicts;
}
