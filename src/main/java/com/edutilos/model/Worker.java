package com.edutilos.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="worker")
@Data
@NamedQueries({
        @NamedQuery(name="@findByAgeBetweenInclusive", query="from Worker where age >= :minAge and age <= :maxAge"),
        @NamedQuery(name="@findByAgeBetweenExclusive", query="from Worker where age > :minAge and age < :maxAge")
})
@NamedNativeQueries({
        @NamedNativeQuery(name="@findByWageBetweenInclusive", query = "select * from worker where wage >= :minWage and wage <= :maxWage"),
        @NamedNativeQuery(name="@findByWageBetweenExclusive", query="select * from worker where wage > :minWage and wage < :maxWage")
})
public class Worker {
    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;
    @Column(name="name")
    private String name;
    @Column(name="age")
    private int age;
    @Column(name="wage")
    private double wage;
    @Column(name="active")
    private boolean active;

    public Worker(long id, String name, int age, double wage, boolean active) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.wage = wage;
        this.active = active;
    }

    public Worker(String name, int age, double wage, boolean active) {
        this.name = name;
        this.age = age;
        this.wage = wage;
        this.active = active;
    }

    public Worker() {
    }
}
