package com.ustudy.requestservice.services;

import com.ustudy.requestservice.models.User;
import com.ustudy.requestservice.services.interfaces.EntityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements EntityService<User, Long> {
    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public User getById(Long id) throws Exception {
        return null;
    }
}
