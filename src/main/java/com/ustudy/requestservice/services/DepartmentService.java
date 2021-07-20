package com.ustudy.requestservice.services;

import com.ustudy.requestservice.models.Department;
import com.ustudy.requestservice.repositories.DepartmentRepository;
import com.ustudy.requestservice.services.interfaces.EntityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentService  implements EntityService<Department,Long> {
    private final DepartmentRepository departmentRepository;
    @Override
    public Department save(Department entity) {
        return null;
    }

    @Override
    public Department update(Department entity) {
        return null;
    }

    @Override
    public void delete(Department entity) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Department getById(Long id) throws Exception {
        return departmentRepository.findById(id).get();
    }
}
