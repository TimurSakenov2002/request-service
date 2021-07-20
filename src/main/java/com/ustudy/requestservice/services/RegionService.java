package com.ustudy.requestservice.services;

import com.ustudy.requestservice.models.Region;
import com.ustudy.requestservice.repositories.RegionRepository;
import com.ustudy.requestservice.services.interfaces.EntityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegionService  implements EntityService<Region,Long> {
    private final RegionRepository regionRepository;
    @Override
    public Region save(Region entity) {
        return null;
    }

    @Override
    public Region update(Region entity) {
        return null;
    }

    @Override
    public void delete(Region entity) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Region getById(Long id) throws Exception {
        return regionRepository.findById(id).get();
    }

    public Region getByName(String name) {
        return regionRepository.findByName(name);
    }
}
