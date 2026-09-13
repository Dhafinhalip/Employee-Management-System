package com.enigmacamp.service.impl;

import com.enigmacamp.dao.DepartementDao;
import com.enigmacamp.entity.Department;
import com.enigmacamp.service.DepartementService;

import java.util.List;
import java.util.Optional;

public class DepartementServiceImpl implements DepartementService {

    private DepartementDao departementDao;

    public DepartementServiceImpl(DepartementDao departementDao) {
        this.departementDao = departementDao;
    }

    @Override
    public Department create(Department department) {
        validateDepartment(department);

        return departementDao.save(department);

    }

    @Override
    public List<Department> getAll() {
        return departementDao.findAll();
    }

    @Override
    public Optional<Department> getById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Department ID must be greater than 0"
            );
        }

        return departementDao.findById(id);

    }

    @Override
    public Optional<Department> getByName(String name) {

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException(
                    "Department name cannot be empty"
            );
        }

        return  departementDao.findByName(name);
    }

    @Override
    public Department update(Department department) {
        if (getById(department.getId()).isEmpty()) {
            throw new IllegalArgumentException(
                    "Department with ID " + department.getId() + " Not Found"
            );
        }

        validateDepartment(department);

        return departementDao.update(department);

    }

    @Override
    public void delete(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Department ID must be greater than 0"
            );
        }

        try {
            departementDao.deleteById(id);
            System.out.println("Department Data Succesfully Delete");
        }catch (Exception e) {
            throw e;
        }
    }



    private void validateDepartment(Department department) {
        if (department == null) {
            throw new IllegalArgumentException(
                    "Department cannot be null"
            );
        }

        if (department.getName() == null ||
                department.getName().isBlank()) {

            throw new IllegalArgumentException(
                    "Department name cannot be empty"
            );
        }

        if (getByName(department.getName().toUpperCase()).isPresent()) {
            throw new IllegalArgumentException(
                    "Department name cannot be duplicate"
            );
        }
    }
}
