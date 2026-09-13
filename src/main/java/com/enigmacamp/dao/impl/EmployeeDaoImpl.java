package com.enigmacamp.dao.impl;

import com.enigmacamp.config.JPAConfig;
import com.enigmacamp.dao.EmployeeDao;
import com.enigmacamp.entity.Department;
import com.enigmacamp.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public Employee save(Employee employee) {
        EntityTransaction tx = null;

        try (EntityManager entityManager = JPAConfig.connect()) {
            tx = entityManager.getTransaction();
            tx.begin();

            entityManager.persist(employee);

            tx.commit();

            return employee;

        } catch (Exception e) {

            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            throw e;

        }
    }

    @Override
    public List<Employee> findAll() {
        try(EntityManager entityManager = JPAConfig.connect()) {
            return entityManager.createQuery("SELECT e FROM Employee e",
                            Employee.class)
                    .getResultList();
        }
    }

    @Override
    public Optional<Employee> findById(Long id) {
        try (EntityManager entityManager = JPAConfig.connect()) {
            Employee employee = entityManager.find(Employee.class, id);

            return Optional.ofNullable(employee);
        }
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        try (EntityManager entityManager = JPAConfig.connect()) {

            String selectQuery = "SELECT e FROM Employee e WHERE e.email = :email";

            TypedQuery<Employee> query = entityManager.createQuery(selectQuery, Employee.class);

            query.setParameter("email", email);

            return query.getResultStream().findFirst();
        }
    }

    @Override
    public Employee update(Employee employee) {
        EntityTransaction tx = null;

        try (EntityManager entityManager =
                     JPAConfig.connect();) {
            tx = entityManager.getTransaction();

            tx.begin();

            Employee updatedEmployee =
                    entityManager.merge(employee);

            tx.commit();

            return updatedEmployee;

        } catch (Exception e) {

            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            throw e;

        }
    }

    @Override
    public void deleteById(Long id) {
        EntityTransaction tx = null;
        try (EntityManager entityManager =
                     JPAConfig.connect();) {
            tx = entityManager.getTransaction();
            tx.begin();

            Employee employee =
                    entityManager.find(Employee.class, id);

            if (employee != null) {
                entityManager.remove(employee);
            }

            tx.commit();

        } catch (Exception e) {

            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            throw e;

        }
    }
}
