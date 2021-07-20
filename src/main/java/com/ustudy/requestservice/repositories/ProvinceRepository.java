package com.ustudy.requestservice.repositories;

import com.ustudy.requestservice.models.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Province findByName(String name);
}
