package com.enigmacamp.service;

import com.enigmacamp.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee create(Employee employee);

    List<Employee> getAll();

    Optional<Employee> getById(Long id);

    Employee update(Employee employee);

    void delete(Long id);
}
