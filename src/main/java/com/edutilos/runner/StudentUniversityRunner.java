package com.edutilos.runner;


import com.edutilos.model.Student;
import com.edutilos.model.University;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  testing @OneToMany and @ManyToOne
 */
public class StudentUniversityRunner {
    public static void main(String[] args) {
        initSessionFactory();
        addData();
        printData();
        updateData();
        printData();
        deleteData();
        printData();
        printDataById(6L);
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
        Session session = factory.openSession();
        try {
           session.getTransaction().begin();
           Student s1, s2, s3, s4;
           s1 = new Student("foo", 10, "1234");
           s2 = new Student("bar", 20, "2345");
           s3 = new Student("edu", 30, "3456");
           s4 = new Student("tilos", 40, "4567");
           University u1, u2;
           u1 = new University("RUB", "Germany", "Bochum", "44801", 80.13);
           u2 = new University("Essen Universität", "Germany", "Esen", "41023", 88.32);
           s1.setUniversity(u1);
           s2.setUniversity(u1);
           s3.setUniversity(u2);
           s4.setUniversity(u2);
           u1.setStudents(Stream.of(s1, s2).collect(Collectors.toList()));
           u2.setStudents(Stream.of(s3,s4).collect(Collectors.toList()));
           session.save(s1);
           session.save(s2);
           session.save(s3);
           session.save(s4);
           session.save(u1);
           session.save(u2);
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
            University u1 = session.find(University.class, 5L);
            u1.getStudents().get(0).setName("new-foo");
            u1.getStudents().get(1).setAge(66);
            session.merge(u1);
            Student s3 = session.find(Student.class, 3L);
            s3.getUniversity().setCity("Dortmund");
            s3.getUniversity().setName("Dortmund Universitaet");
            s3.setAge(6666);
            s3.setName("old-edu");
            session.merge(s3);
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
            University u1 = session.find(University.class, 5L);
            session.delete(u1);
            u1.getStudents().forEach(session::delete);
            session.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    private static void printDataById(long uniId) {
        Session session = factory.openSession();
        University u2 = session.createQuery("from University where id = :id", University.class)
                .setParameter("id", uniId)
                .getSingleResult();
        System.out.println(u2.toString());
        Student s3 = session.find(Student.class, 3L);
        System.out.println(s3.toString());
        session.close();
    }

    private static void printData() {
        Session session = factory.openSession();
        List<University> allUnis = session.createQuery("from University", University.class)
                .getResultList();
        allUnis.forEach(System.out::println);
        session.close();
    }
}
