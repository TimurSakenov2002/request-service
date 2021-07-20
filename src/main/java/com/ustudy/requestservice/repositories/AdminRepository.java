package com.ustudy.requestservice.repositories;

import com.ustudy.requestservice.models.Admin;
import com.ustudy.requestservice.models.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Boolean existsByUserDetailEmail(String userDetail_email);

    Admin getByUserDetailEmail(String userDetail_email);
}
