package com.darfik.skycast.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory = buildSessionFactory();
    private static boolean useTestConfiguration = false;

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            if (useTestConfiguration) {
                configuration.configure("hibernate-test.cfg.xml");
            } else {
                configuration.configure();
            }
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void useTestConfiguration(boolean useTestConfig) {
        useTestConfiguration = useTestConfig;
        sessionFactory = buildSessionFactory();
    }
}
