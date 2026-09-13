package com.enigmacamp.dao;

import com.enigmacamp.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    Employee save(Employee employee);

    List<Employee> findAll();

    Employee findById(Long id);

    Employee findByName(String name);

    List<Employee> findByDepartment(String department);

    Employee findByEmail(String email);

    Employee update(Employee employee);

    void deleteById(Long id);
}
