package com.edutilos.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="husband")
@Data
public class Husband {
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
    @OneToOne(cascade = CascadeType.ALL)
    private Wife wife;

    public Husband(long id, String name, int age, double wage, Wife wife) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.wage = wage;
        this.wife = wife;
    }

    public Husband(String name, int age, double wage, Wife wife) {
        this.name = name;
        this.age = age;
        this.wage = wage;
        this.wife = wife;
    }

    public Husband() {
    }
}
