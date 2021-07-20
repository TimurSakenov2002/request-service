package com.ustudy.requestservice.repositories;

import com.ustudy.requestservice.models.Auditory;
import com.ustudy.requestservice.models.SessionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRequestRepository extends JpaRepository<SessionRequest,Long> {
    List<SessionRequest> findAllByAuditory(Auditory auditory);
    Long countAllByAuditory(Auditory auditory);
}
