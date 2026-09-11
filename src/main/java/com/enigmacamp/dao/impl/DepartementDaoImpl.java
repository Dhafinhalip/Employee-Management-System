package com.enigmacamp.dao.impl;

import com.enigmacamp.config.JPAConfig;
import com.enigmacamp.dao.DepartementDao;
import com.enigmacamp.entity.Attendance;
import com.enigmacamp.entity.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;


import java.util.List;
import java.util.Optional;

public class DepartementDaoImpl implements DepartementDao {
    @Override
    public Department save(Department department) {
        EntityTransaction tx = null;

        try (EntityManager entityManager = JPAConfig.connect()) {
            tx = entityManager.getTransaction();
            tx.begin();

            entityManager.persist(department);

            tx.commit();

            return department;

        } catch (Exception e) {

            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            throw e;

        }
    }

    @Override
    public List<Department> findAll() {
        try(EntityManager entityManager = JPAConfig.connect()) {
            return entityManager.createQuery("SELECT d FROM Department d",
                            Department.class)
                    .getResultList();
        }
    }

    @Override
    public Optional<Department> findById(Long id) {
        try (EntityManager entityManager = JPAConfig.connect()) {
            Department department = entityManager.find(Department.class, id);

            return Optional.ofNullable(department);
        }
    }

    @Override
    public Department update(Department department) {
        EntityTransaction tx = null;

        try (EntityManager entityManager =
                     JPAConfig.connect();) {
            tx = entityManager.getTransaction();

            tx.begin();

            Department updatedDepartment =
                    entityManager.merge(department);

            tx.commit();

            return updatedDepartment;

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

            Department department =
                    entityManager.find(Department.class, id);

            if (department != null) {
                entityManager.remove(department);
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
