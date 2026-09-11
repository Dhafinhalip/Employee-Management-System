package com.enigmacamp.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfig {
    private static final String PERSISTENCE_UNIT_NAME = "jpa-lab";
    private static EntityManagerFactory emf;

    //    private JPAConfig() {}
    private static EntityManagerFactory getEntityManagerFactory() {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            System.out.println("Success to connect");
            // proses untuk menangangi gangguan, yang mengharuskan kita close
        } catch (Exception e) {
            System.err.println("Failed to create EntityManagerFactory..." + e.getMessage());
            throw new RuntimeException("Could not create EntityManagerFactory", e);
        }
        return emf;
    }

    public static EntityManager connect() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static void disconnect() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}