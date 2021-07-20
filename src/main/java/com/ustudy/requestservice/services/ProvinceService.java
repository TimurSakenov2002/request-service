package com.ustudy.requestservice.services;

import com.ustudy.requestservice.models.Province;
import com.ustudy.requestservice.repositories.ProvinceRepository;
import com.ustudy.requestservice.services.interfaces.EntityService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class ProvinceService implements EntityService<Province, Long> {
    private final ProvinceRepository provinceRepository;

    public CompletableFuture<List<Province>> asyncProcess() {
        return CompletableFuture.completedFuture(provinceRepository.findAll());
    }

    @Async("threadPoolTaskExecutor")
    @Cacheable(value = "provinces")
    public CompletableFuture<List<Province>> getAllProvinces() {
        return asyncProcess();
    }


    @Override
    public Province save(Province entity) {
        return provinceRepository.save(entity);
    }

    @Override
    public Province update(Province entity) {
        return provinceRepository.save(entity);
    }

    @Override
    public void delete(Province entity) {
        provinceRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        provinceRepository.deleteById(id);
    }

    @Override
    public Province getById(Long id) throws Exception {
        return provinceRepository.findById(id).get();
    }

    public Province getByName(String name){
        return provinceRepository.findByName(name);
    }
}
