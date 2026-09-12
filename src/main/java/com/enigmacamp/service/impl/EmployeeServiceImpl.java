package com.enigmacamp.service.impl;

import com.enigmacamp.dao.EmployeeDao;
import com.enigmacamp.entity.Employee;
import com.enigmacamp.service.DepartementService;
import com.enigmacamp.service.EmployeeService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao;
    private DepartementService departementService;

    public EmployeeServiceImpl(EmployeeDao employeeDao, DepartementService departementService) {
        this.employeeDao = employeeDao;
        this.departementService = departementService;
    }

    @Override
    public Employee create(Employee employee) {
        validateEmployee(employee);

        return employeeDao.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.findAll();
    }

    @Override
    public Optional<Employee> getById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be greater than 0"
            );
        }

        return employeeDao.findById(id);
    }

    @Override
    public Employee update(Employee employee) {
        if (getById(employee.getId()).isEmpty()) {
            throw new IllegalArgumentException(
                    "Department with ID" + employee.getId() + "Not Found"
            );
        }

        validateEmployee(employee);

        return employeeDao.update(employee);

    }

    @Override
    public void delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be greater than 0"
            );
        }

        try {
            employeeDao.deleteById(id);
            System.out.println("Employee Data Successfully Delete");

        } catch (Exception e) {
            throw e;
        }
    }

    private void validateEmployee(Employee employee) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern p = Pattern.compile(emailRegex);

        if (employee == null) {
            throw new IllegalArgumentException(
                    "Employee cannot be null"
            );
        }

        if (employee.getFullname() == null ||
                employee.getFullname().isBlank()) {

            throw new IllegalArgumentException(
                    "Employee name cannot be empty"
            );
        }

        if (employee.getEmail() == null ) {
            throw new IllegalArgumentException(
                    "Employee Email cannot be empty"
            );
        }

        Matcher m = p.matcher(employee.getEmail());
        if (!m.matches()) {
            throw new IllegalArgumentException(
                    "Employee Email Is Not Valid"
            );
        }

        if (employee.getAddress() == null ||
                employee.getAddress().isBlank()) {

            throw new IllegalArgumentException(
                    "Employee address cannot be empty"
            );
        }

        if (departementService.getById(employee.getDepartment().getId()).isEmpty()) {
            throw new NullPointerException(
                    "Department must exist."
            );
        }
    }
}
