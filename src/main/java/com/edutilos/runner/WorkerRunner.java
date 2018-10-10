package com.edutilos.runner;

import com.edutilos.model.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class WorkerRunner {
    public static void main(String[] args) {
        initSessionFactory();
        saveWorker(new Worker("foo", 10, 100.0, true));
        saveWorker(new Worker("bar", 20, 200.0, false));
        saveWorker(new Worker("bim", 30, 300.0, true));
        printWorkers();
        updateWorker(new Worker("new-foo", 66, 666.6, false), 1L);
        printWorkers();
        System.out.println("Before delete:");
        printWorkerById(2L);
        printWorkers();
        deleteWorker(2L);
        System.out.println("After delete: ");
        printWorkers();
        repopulateWorker();
        testNamedQueries();
        testNamedNativeQueries();
        closeSessionFactory();
    }
    private static SessionFactory factory;

    private static void initSessionFactory() {
        factory = new Configuration().configure().buildSessionFactory();
    }
    private static void closeSessionFactory() {
        if(factory!= null && !factory.isClosed()) factory.close();
    }

    private static void saveWorker(Worker worker) {
        Session session =  factory.openSession();
        try  {
            session.getTransaction().begin();
            session.save(worker);
            session.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    private static void updateWorker(Worker newWorker, long id) {
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            Worker oldWorker = session.get(Worker.class, id);
            oldWorker.setName(newWorker.getName());
            oldWorker.setAge(newWorker.getAge());
            oldWorker.setWage(newWorker.getWage());
            oldWorker.setActive(newWorker.isActive());
            session.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    private static void deleteWorker(Long id) {
        Session session = factory.openSession();
        Worker w = session.find(Worker.class, id);
        session.close();
        deleteWorker(w);
    }

    private static void deleteWorker(Worker w) {
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            session.delete(w);
            session.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    private static void printWorkerById(Long id) {
        Session session = factory.openSession();
        Worker w = session.find(Worker.class, id);
        System.out.println(String.format("with session.find() = %s",w.toString()));
        w = session.createQuery("from Worker where id = :id", Worker.class)
                .setParameter("id", id)
                .getSingleResult();
        System.out.println(String.format("with session.createQuery().getSingleResult() = %s", w.toString()));
        session.close();
    }

    private static void printWorkers() {
        Session session = factory.openSession();
        List<Worker> workers = session.createQuery("from Worker", Worker.class)
                .getResultList();
        session.close();
        System.out.println("<<All workers>>");
        workers.forEach(w-> System.out.println(w));
    }

    private static void testNamedQueries() {
        Session session = factory.openSession();
        List<Worker> workers = session.createNamedQuery("@findByAgeBetweenInclusive", Worker.class)
                .setParameter("minAge", 10)
                .setParameter("maxAge", 30)
                .getResultList();
        System.out.println("<<NamedQueries>>");
        workers.forEach(System.out::println);
        System.out.println();
        workers = session.createNamedQuery("@findByAgeBetweenExclusive", Worker.class)
                .setParameter("minAge", 10)
                .setParameter("maxAge", 30)
                .getResultList();
        workers.forEach(System.out::println);
        System.out.println();
        session.close();
    }


    private static Worker convertArrayToWorker(Object [] data) {
        return new Worker(Long.parseLong(data[0].toString()),data[3].toString(), Integer.parseInt(data[2].toString()),
                Double.parseDouble(data[4].toString()), Boolean.parseBoolean(data[1].toString()));
    }

    private static void testNamedNativeQueries() {
        Session session = factory.openSession();
        @SuppressWarnings("unchecked")
        List<Object[]> workers = session.getNamedNativeQuery("@findByWageBetweenInclusive")
                .setParameter("minWage", 100.0)
                .setParameter("maxWage", 300.0)
                .getResultList();
        System.out.println("<<NamedNativeQueries>>");
        workers.forEach(w-> System.out.println(convertArrayToWorker(w)));
        System.out.println();

        workers = session.getNamedNativeQuery("@findByWageBetweenExclusive")
                .setParameter("minWage", 100.0)
                .setParameter("maxWage", 300.0)
                .getResultList();
        workers.forEach(w-> System.out.println(convertArrayToWorker(w)));
        System.out.println();
        session.close();
    }

    private static void repopulateWorker() {
        Session session = factory.openSession();
        List<Worker> all = session.createQuery("from Worker", Worker.class).getResultList();
        session.close();
        all.forEach(WorkerRunner::deleteWorker);
        saveWorker(new Worker("foo", 10, 100.0, true));
        saveWorker(new Worker("bar", 20, 200.0, false));
        saveWorker(new Worker("bim", 30, 300.0, true));
        saveWorker(new Worker("pako",40, 400.0, false ));

    }

}
