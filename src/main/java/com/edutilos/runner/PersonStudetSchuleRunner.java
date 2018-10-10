package com.edutilos.runner;

import com.personTest.model.PersonStudent;
import com.personTest.model.Personadress;
import com.personTest.model.Personschule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersonStudetSchuleRunner {
    public static void main(String[] args) {
        initSessionFactory();
        testCreate();
//        testUpdate();
       // testDelete();
        findData();
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
            Personadress adr=new Personadress(1000,"bilmem ne str","45141",6);
            //   PersonTest pt= new PersonTest( 1001,"cettmil","r");
            //   pt.setAdress(adr);
            //  adr.setPersonTest(pt);
            Personschule schule=new Personschule();
            schule.setId(1000);
            schule.setLeite("armut");
            schule.setName("ilkoluk");


            PersonStudent student=new PersonStudent();
            student.setVorname("nijat");
            student.setName("akayev");
            student.setAdress(adr);
            student.setId(1001);
            student.setKlasse(1);
            student.setScholl("azerbaycan");
            student.setSchule(schule);
            schule.setStudent(Stream.of(student).collect(Collectors.toSet()));
            session.save(student);
            session.getTransaction().commit();
        } catch(Exception ex)  {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    private static void testUpdate() {
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            Personschule personschule = session.find(Personschule.class, 1000);
            personschule.getStudent().forEach(student -> {
                student.setName("updated "+ student.getName());
            });
            session.merge(personschule);

            PersonStudent student = session.find(PersonStudent.class, 1000);
            student.setVorname("updated "+ student.getVorname());
            session.merge(student);
            session.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    private static void testDelete() {
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            Personschule personschule = session.find(Personschule.class, 1000);
            personschule = session.createQuery("from Personschule where id = :id",Personschule.class)
                    .setParameter("id", 1000)
                    .getSingleResult();
            session.delete(personschule);
            session.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    private static void findData() {
        Session session = factory.openSession();
        List<Personschule> all = session.createQuery("from Personschule", Personschule.class).getResultList();
        all.forEach(System.out::println);
        session.close();
    }
}
