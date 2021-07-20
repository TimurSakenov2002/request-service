package com.ustudy.requestservice.services;

import com.ustudy.requestservice.models.Auditory;
import com.ustudy.requestservice.models.Province;
import com.ustudy.requestservice.repositories.AuditoryRepository;
import com.ustudy.requestservice.services.interfaces.EntityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuditoryService implements EntityService<Auditory, Long> {
    private final AuditoryRepository auditoryRepository;

    @Override
    public Auditory save(Auditory entity) {
        return null;
    }

    @Override
    public Auditory update(Auditory entity) {
        return null;
    }

    @Override
    public void delete(Auditory entity) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Auditory getById(Long id) throws Exception {
        return auditoryRepository.findById(id).get();
    }

    public List<Auditory> getAlLByProvince(Province province) {
        return auditoryRepository.findAllByDepartmentRegionProvince(province);
    }

}
