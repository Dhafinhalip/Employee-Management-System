package com.enigmacamp.dao.impl;

import com.enigmacamp.config.JPAConfig;
import com.enigmacamp.dao.EmployeeDao;
import com.enigmacamp.entity.Department;
import com.enigmacamp.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Employee findById(Long id) {
        try (EntityManager entityManager = JPAConfig.connect()) {
            Employee employee = entityManager.find(Employee.class, id);

            return employee;
        }
    }

    @Override
    public Employee findByName(String name) {
        try (EntityManager entityManager = JPAConfig.connect()) {

            String selectQuery = "SELECT e FROM Employee e WHERE e.name = :name";

            TypedQuery<Employee> query = entityManager.createQuery(selectQuery, Employee.class);

            query.setParameter("name", name);

            Employee employee = query.getSingleResult();

            return employee;
        }
    }

    @Override
    public List<Employee> findByDepartment(String department) {
        try (EntityManager entityManager = JPAConfig.connect()) {

            String selectQuery = "SELECT e FROM Employee e WHERE e.department = :department";

            TypedQuery<Employee> query = entityManager.createQuery(selectQuery, Employee.class);

            query.setParameter("department", department);

            List<Employee> employee = query.getResultList();

            return employee;
        }
    }

    @Override
    public Employee findByEmail(String email) {
        try (EntityManager entityManager = JPAConfig.connect()) {

            String selectQuery = "SELECT e FROM Employee e WHERE e.email = :email";

            TypedQuery<Employee> query = entityManager.createQuery(selectQuery, Employee.class);

            query.setParameter("email", email);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return  null;
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
