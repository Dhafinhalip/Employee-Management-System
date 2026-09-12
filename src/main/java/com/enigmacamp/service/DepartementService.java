package com.enigmacamp.service;

import com.enigmacamp.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartementService {
    Department create(Department department);

    List<Department> getAll();

    Optional<Department> getById(Long id);

    Optional<Department> getByName(String name);

    Department update(Department department);

    void delete(Long id);

    void validateDepartment (Department department);



}
