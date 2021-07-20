package com.ustudy.requestservice.repositories;

import com.ustudy.requestservice.models.Auditory;
import com.ustudy.requestservice.models.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoryRepository extends JpaRepository<Auditory, Long> {
    List<Auditory> findAllByDepartmentRegionProvince(Province department_region_province);
}
