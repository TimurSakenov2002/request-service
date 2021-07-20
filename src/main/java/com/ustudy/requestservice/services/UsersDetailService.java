package com.ustudy.requestservice.services;

import com.ustudy.requestservice.models.User;
import com.ustudy.requestservice.models.UserDetail;
import com.ustudy.requestservice.repositories.UserDetailRepository;
import com.ustudy.requestservice.services.interfaces.EntityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersDetailService implements UserDetailsService, EntityService<UserDetail, Long> {
    private final UserDetailRepository userDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetail user = userDetailRepository.findByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return user;
    }

    @Override
    public UserDetail save(UserDetail entity) {
//        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return userDetailRepository.save(entity);
    }

    @Override
    public UserDetail update(UserDetail entity) {
        return save(entity);
    }

    @Override
    public void delete(UserDetail entity) {
        userDetailRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        userDetailRepository.deleteById(id);
    }

    @Override
    public UserDetail getById(Long id) throws Exception {
        return userDetailRepository.getById(id);
    }
}
