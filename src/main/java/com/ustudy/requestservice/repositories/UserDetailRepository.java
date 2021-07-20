package com.ustudy.requestservice.repositories;

import com.ustudy.requestservice.models.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    UserDetail findByEmail(String email);
}
