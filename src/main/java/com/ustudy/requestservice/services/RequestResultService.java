package com.ustudy.requestservice.services;

import com.ustudy.requestservice.dto.RequestResultsWithConflicts;
import com.ustudy.requestservice.models.Auditory;
import com.ustudy.requestservice.models.RequestResult;
import com.ustudy.requestservice.models.SessionRequest;
import com.ustudy.requestservice.repositories.RequestResultRepository;
import com.ustudy.requestservice.services.interfaces.EntityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class RequestResultService implements EntityService<RequestResult, Long> {
    private final SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");

    private final RequestResultRepository requestResultRepository;

    @Override
    public RequestResult save(RequestResult entity) {
        return requestResultRepository.save(entity);
    }

    @Override
    public RequestResult update(RequestResult entity) {
        return save(entity);
    }

    @Override
    public void delete(RequestResult entity) {
        requestResultRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        requestResultRepository.deleteById(id);
    }

    @Override
    public RequestResult getById(Long id) throws Exception {
        return requestResultRepository.findById(id).get();
    }

    public RequestResultsWithConflicts getResultsByAuditoryAndDate(Auditory auditory, Date date) {
        RequestResultsWithConflicts resultsWithConflicts = new RequestResultsWithConflicts();


        List<RequestResult> resultList = requestResultRepository.findAllByAcceptedNullAndSessionRequestAuditory(auditory);
        resultList.removeIf(requestResult -> !fmt.format(requestResult.getSessionRequest().getStartDate()).equals(fmt.format(date)));
        resultList.removeIf(requestResult -> requestResult.getSuggestion()!=null);

        List<SessionRequest> sessionRequests = new ArrayList<>();
        resultList.forEach(e -> sessionRequests.add(e.getSessionRequest()));

        Collections.sort(sessionRequests);

        resultsWithConflicts.setSessionRequests(sessionRequests);
        resultsWithConflicts.setSessionConflicts(getConflictedSessions(sessionRequests));
        return resultsWithConflicts;

    }

    public List<RequestResult> getMyRequestResults(String username){
        return requestResultRepository.findAllByUsername(username);
    }

    public Map<Long, Long> getConflictedSessions(List<SessionRequest> sessionRequests) {
        Map<Long, Long> conflictList = new HashMap<>();
        for (int i = 1; i < sessionRequests.size(); i++) {
            Date previous = sessionRequests.get(i - 1).getEndDate();
            Date next = sessionRequests.get(i).getStartDate();

            if (previous.getHours() >= next.getHours()) {
                conflictList.put(sessionRequests.get(i - 1).getId(), sessionRequests.get(i).getId());
                conflictList.put(sessionRequests.get(i).getId(), sessionRequests.get(i - 1).getId());

                if (previous.getMinutes() >= next.getMinutes()) {
                    conflictList.putIfAbsent(sessionRequests.get(i - 1).getId(), sessionRequests.get(i).getId());
                    conflictList.putIfAbsent(sessionRequests.get(i).getId(), sessionRequests.get(i - 1).getId());
                }
            }
        }
        return conflictList;
    }

    public void acceptResult(Long requestResult1) throws Exception {
        RequestResult requestResult=getById(requestResult1);
        requestResult.setIsSeen(true);
        requestResult.setAccepted(true);
        save(requestResult);
    }

    public void rejectResult(Long requestResult1) throws Exception {
        RequestResult requestResult=getById(requestResult1);
        requestResult.setIsSeen(true);
        requestResult.setAccepted(false);
        save(requestResult);
    }

}
