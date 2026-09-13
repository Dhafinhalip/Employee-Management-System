package com.enigmacamp.service;

import com.enigmacamp.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee create(Employee employee);

    List<Employee> getAll();

    Employee getById(Long id);

    Employee getByName(String name);

    Employee getByEmail(String email);

    Employee update(Employee employee);

    void delete(Long id);

}
