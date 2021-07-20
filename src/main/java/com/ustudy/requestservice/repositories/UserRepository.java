package com.ustudy.requestservice.repositories;

import com.ustudy.requestservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User getByUserDetailEmail(String userDetail_email);
}
