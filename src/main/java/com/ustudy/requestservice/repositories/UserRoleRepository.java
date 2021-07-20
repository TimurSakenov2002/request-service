package com.ustudy.requestservice.repositories;

import com.ustudy.requestservice.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    UserRole getByName(String name);
}
