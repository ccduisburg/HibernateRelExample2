package com.edutilos.runner;

import com.edutilos.model.Husband;
import com.edutilos.model.Wife;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HusbandWifeRunner {
    public static void main(String[] args) {
        initSessionFactory();
        addData();
        printAll();
        updateData();
        printAll();
        deleteData();
        printAll();
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

    private static void addData() {
        Wife w1, w2;
        w1 = new Wife("bar", 10, 100.0);
        w2 = new Wife("tilos", 20, 200.0);
        Husband h1, h2;
        h1 = new Husband("foo", 10, 100.0, w1);
        h2 = new Husband("edu", 20, 200.0, w2);
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            session.save(h1);
            session.save(h2);
            session.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
    private static void deleteData() {
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            Husband h = session.find(Husband.class, 1L);
            session.delete(h);
            session.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
    private static void updateData() {
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            Husband husband = session.find(Husband.class, 1L);
            husband.setWife(new Wife("new-bar", 30, 300.0));
            session.merge(husband);
            session.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
    private static void printAll() {
        Session session = factory.openSession();
        List<Husband> husbands = session.createQuery("from Husband", Husband.class)
                .getResultList();
        husbands.forEach(System.out::println);
        session.close();
        System.out.println();
    }
}
