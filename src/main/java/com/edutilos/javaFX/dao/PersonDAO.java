package com.edutilos.javaFX.dao;

import com.edutilos.javaFX.model.Person;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;
import java.util.List;



public class PersonDAO implements DAO<Person,Long> {
    public PersonDAO() {
        initSessionFactory();
    }

    private  SessionFactory factory;
    private void initSessionFactory() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    private void closeSessionFactory() {
        if(factory != null && factory.isOpen())
            factory.close();
    }



    @Override
    public void create(Person ps) {

        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            session.save(ps);
            session.getTransaction().commit();
        } catch(Exception ex)  {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }


    }

    @Override
    public void update(Long id, Person ps) {
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            Person p=find(id);
            p.setName(ps.getName());
            p.setWage(ps.getWage());
            p.setAge(ps.getAge());
            session.merge(p);
            session.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Long id) {
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
//            find(id);
           Person person = session.createQuery("from Person where id = :id",Person.class)
                    .setParameter("id", id)
                    .getSingleResult();
            session.delete(person);
            session.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public Person find(Long id) {
        Session session = factory.openSession();
        try {
            return  session.createQuery("from Person where id = :id",Person.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }



    public Person findByName(String name) {
        Session session = factory.openSession();

        Person person = session.createQuery("from Person where name = :name",Person.class)
                .setParameter("name", name)
                .getSingleResult();

        session.close();
        return person;
    }

    @Override
    public List<Person> findAll() {
        Session session = factory.openSession();
        List<Person> allUnis = session.createQuery("from Person", Person.class)
                .getResultList();
        session.close();
        return allUnis;
    }


}
