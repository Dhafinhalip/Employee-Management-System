package com.enigmacamp.dao.impl;

import com.enigmacamp.config.JPAConfig;
import com.enigmacamp.dao.AttendanceDao;
import com.enigmacamp.entity.Attendance;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;


import java.util.List;
import java.util.Optional;

public class AttendanceDaoImpl implements AttendanceDao {
    @Override
    public Attendance save(Attendance attendance) {
        EntityTransaction tx = null;

        try (EntityManager entityManager = JPAConfig.connect()) {
            tx = entityManager.getTransaction();
            tx.begin();

            entityManager.persist(attendance);

            tx.commit();

            return attendance;

        } catch (Exception e) {

            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            throw e;

        }

    }

    @Override
    public List<Attendance> findAll() {
        try(EntityManager entityManager = JPAConfig.connect()) {
            return entityManager.createQuery("SELECT a FROM Attendance a",
                    Attendance.class)
                    .getResultList();
        }
    }

    @Override
    public Optional<Attendance> findById(Long id) {
        try (EntityManager entityManager = JPAConfig.connect()) {
            Attendance attendance = entityManager.find(Attendance.class, id);

            return Optional.ofNullable(attendance);
        }
    }

    @Override
    public Attendance update(Attendance attendance) {
        EntityTransaction tx = null;

        try (EntityManager entityManager =
                     JPAConfig.connect();) {
            tx = entityManager.getTransaction();

            tx.begin();

            Attendance updatedAttendance =
                    entityManager.merge(attendance);

            tx.commit();

            return updatedAttendance;

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

            Attendance attendance =
                    entityManager.find(Attendance.class, id);

            if (attendance != null) {
                entityManager.remove(attendance);
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
