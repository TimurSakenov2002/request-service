package com.ustudy.requestservice.repositories;

import com.ustudy.requestservice.models.SessionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionTypeRepository extends JpaRepository<SessionType,Long> {
    SessionType getByName(String name);
}
