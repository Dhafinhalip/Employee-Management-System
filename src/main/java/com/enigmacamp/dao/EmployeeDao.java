package com.enigmacamp.dao;

import com.enigmacamp.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    Employee save(Employee employee);

    List<Employee> findAll();

    Optional<Employee> findById(Long id);

    Optional<Employee> findByEmail(String email);

    Employee update(Employee employee);

    void deleteById(Long id);
}
