package com.edutilos.runner;

import com.edutilos.model.PersonTransient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersonTransientRunner {
    public static void main(String[] args) {
        initSessionFactory();
        testCreate();
        closeSessionFactory();
    }
    private static SessionFactory factory;
    private static void initSessionFactory() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    private static void closeSessionFactory() {
        if(factory != null && factory.isOpen())
            factory.close();
    }
    private static void testCreate() {
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            PersonTransient ps = new PersonTransient();
            ps.setId(1);
            ps.setFname("foo");
            ps.setLname("bar");
            ps.setAge(10);
            ps.setWage(100.0);
            ps.setActive(true);
            ps.setActivities(Stream.of("reading", "writing", "speaking").collect(Collectors.toList()));
            session.save(ps);
            session.getTransaction().commit();
        } catch(Exception ex)  {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
