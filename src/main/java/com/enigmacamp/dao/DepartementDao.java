package com.enigmacamp.dao;

import com.enigmacamp.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartementDao {
    Department save(Department department);

    List<Department> findAll();

    Optional<Department> findById(Long id);

    Department update(Department department);

    void deleteById(Long id);

}
