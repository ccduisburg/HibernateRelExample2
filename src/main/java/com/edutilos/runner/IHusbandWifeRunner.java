package com.edutilos.runner;

import com.edutilos.model.IHusband;
import com.edutilos.model.IWife;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class IHusbandWifeRunner {
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
        IWife w1, w2;
        w1 = new IWife("bar", 10, 100.0);
        w2 = new IWife("tilos", 20, 200.0);
        IHusband h1, h2;
        h1 = new IHusband("foo", 10, 100.0, w1);
        h2 = new IHusband("edu", 20, 200.0, w2);
        w1.setHusband(h1);
        w2.setHusband(h2);
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            session.save(h1);
            session.save(h2);
            session.save(w1);
            session.save(w2);
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
            IHusband h = session.find(IHusband.class, 1L);
            session.delete(h.getWife());
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
            IHusband iHusband = session.find(IHusband.class, 1L);
//            IHusband.setWife(new IWife("new-bar", 30, 300.0));
            iHusband.getWife().setName("new-bar");
            iHusband.getWife().setAge(30);
            iHusband.getWife().setWage(300.0);
            session.merge(iHusband);
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
        List<IHusband> husbands = session.createQuery("from IHusband", IHusband.class)
                .getResultList();
        husbands.forEach(System.out::println);
        session.close();
        System.out.println();
    }
}
