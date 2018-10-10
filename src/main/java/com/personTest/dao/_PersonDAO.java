package com.personTest.dao;


import com.edutilos.javaFX.model.Person;
import com.personTest.model.PersonStudent;
import com.personTest.model.PersonTest;
import com.personTest.model.Personschule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import javax.persistence.NoResultException;
import java.util.List;

public class _PersonDAO implements PDAO<PersonStudent, Integer> {

    public _PersonDAO(){intSessionFactory();}

    private SessionFactory factory;
    private void intSessionFactory() {
        factory=new Configuration().configure().buildSessionFactory();
    }


    private void closeSessionFactory() {
        if(factory != null && factory.isOpen())
            factory.close();
    }


    @Override
    public void create(PersonStudent personStudent) {
        Session session=factory.openSession();
        try{
            session.getTransaction().begin();
            session.merge(personStudent);
            session.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
    }

    @Override
    public void update(Integer id, PersonStudent ps) {
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            PersonStudent p=find(id);
            p.setName(ps.getName());
            p.setAdress(ps.getAdress());
            p.setVorname(ps.getVorname());
            p.setAdress(ps.getAdress());
            Personschule personschule = ps.getSchule();
            personschule.setId(p.getSchule().getId());
            p.setSchule(personschule);
            p.setScholl(ps.getScholl());
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
    public void delete(Integer id) {

        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            PersonStudent person = session.createQuery("from PersonStudent where id = :id",PersonStudent.class)
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
    public PersonStudent find(Integer id) {
        Session session = factory.openSession();
        try {
            return  session.createQuery("from PersonStudent where id = :id", PersonStudent.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }


    public PersonStudent findByName(String name) {
        Session session = factory.openSession();
        PersonStudent person = null;
        try {
            String _name = name+'%';
            person = session.createQuery("from PersonStudent where name = :name",PersonStudent.class)
                    .setParameter("name", name)
                    .getSingleResult();

        } catch(Exception ex) {
//            ex.printStackTrace();
        } finally {
            session.close();
        }
        return person;
    }

    @Override
    public List<PersonStudent> findAll() {
        Session session = factory.openSession();
        List<PersonStudent> allUnis = session.createQuery("from PersonStudent", PersonStudent.class)
                .getResultList();
        session.close();
        return allUnis;
    }

}
