package com.edutilos.runner;

import com.edutilos.model.Teacher;
import com.edutilos.model.University;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * testing @ManyToMany
 */
public class TeacherUniversityRunner {
    public static void main(String[] args) {
        initSessionFactory();
        addData();
        printData();
        System.out.println("<<After Update>>");
        updateData();
        printData();
        System.out.println("<<After Delete>>");
        deleteData();
        printData();
        printDataById(2L, 4L);
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
            University u1, u2;
            Teacher t1, t2;
            u1 = new University("RUB", "Germany", "Bochum", "44801", 80.12);
            u2 = new University("Essen Universität", "Germany", "Essen", "44701", 83.23);
            t1 = new Teacher("foobar", 10, 100.0, Stream.of("Chemistry", "Biochemistry", "Microchemistry").collect(Collectors.toList()));
            t2 = new Teacher("leomessi", 20, 200.0, Stream.of("History", "Geography", "Literature").collect(Collectors.toList()));
            u1.setTeachers(Stream.of(t1,t2).collect(Collectors.toList()));
            u2.setTeachers(Stream.of(t2).collect(Collectors.toList()));
            t1.setUniversities(Stream.of(u1).collect(Collectors.toList()));
            t2.setUniversities(Stream.of(u1,u2).collect(Collectors.toList()));
            session.save(u1);
            session.save(u2);
            session.save(t1);
            session.save(t2);
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
            University u1 = session.find(University.class, 1L);
            u1.getTeachers().get(0).setName("new-edutilos");
            session.merge(u1);
            Teacher t2 = session.find(Teacher.class, 4L);
            t2.getUniversities().get(1).setName("Dortmund Universität");
            t2.getUniversities().get(1).setCity("Dortmund");
            t2.getUniversities().get(1).setPostcode("58012");
            session.merge(t2);
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
            University u1 = session.createQuery("from University where id = :id", University.class)
                    .setParameter("id", 1L)
                    .getSingleResult();
            u1.getStudents().forEach(session::delete);
            session.delete(u1.getTeachers().get(0));
            session.delete(u1);
            session.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
    private static void printDataById(long uniId, long teacherId) {
        Session session = factory.openSession();
        University u2 = session.find(University.class, uniId);
        Teacher t2 = session.find(Teacher.class, teacherId);
        System.out.println("<<printDataById: uniId = "+ uniId+">>");
        System.out.println(u2.toString());
        System.out.println(String.format("<<printDataById: teacherId = %d>>", teacherId));
        System.out.println(t2.toString());
        session.close();
    }
    private static void printData() {
        Session session = factory.openSession();
        List<University> allUnis = session.createQuery("from University", University.class)
                .getResultList();
        allUnis.forEach(System.out::println);
        List<Teacher> allTeachers = session.createQuery("from Teacher", Teacher.class)
                .getResultList();
        allTeachers.forEach(System.out::println);
        System.out.println();
        session.close();
    }
}
