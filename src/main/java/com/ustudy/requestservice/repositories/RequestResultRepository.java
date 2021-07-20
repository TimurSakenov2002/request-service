package com.ustudy.requestservice.repositories;

import com.ustudy.requestservice.models.Auditory;
import com.ustudy.requestservice.models.Department;
import com.ustudy.requestservice.models.RequestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestResultRepository extends JpaRepository<RequestResult,Long> {
    List<RequestResult> findAllByAcceptedNullAndSessionRequestAuditory(Auditory sessionRequest_auditory);
    @Query("select o from RequestResult o  where o.sessionRequest.user.userDetail.email=?1")
    List<RequestResult> findAllByUsername(String username);
}
