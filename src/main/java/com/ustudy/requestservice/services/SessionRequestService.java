package com.ustudy.requestservice.services;

import com.ustudy.requestservice.models.SessionRequest;
import com.ustudy.requestservice.services.interfaces.EntityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SessionRequestService  implements EntityService<SessionRequest,Long> {
    @Override
    public SessionRequest save(SessionRequest entity) {
        return null;
    }

    @Override
    public SessionRequest update(SessionRequest entity) {
        return null;
    }

    @Override
    public void delete(SessionRequest entity) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public SessionRequest getById(Long id) throws Exception {
        return null;
    }
}
