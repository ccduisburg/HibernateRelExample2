package com.edutilos.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="wife")
@Data
public class Wife {
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

    public Wife(long id, String name, int age, double wage) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.wage = wage;
    }

    public Wife(String name, int age, double wage) {
        this.name = name;
        this.age = age;
        this.wage = wage;
    }

    public Wife() {
    }
}
