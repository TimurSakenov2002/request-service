package com.ustudy.requestservice.repositories;

import com.ustudy.requestservice.models.DepSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepScheduleRepository extends JpaRepository<DepSchedule,Long> {
}
