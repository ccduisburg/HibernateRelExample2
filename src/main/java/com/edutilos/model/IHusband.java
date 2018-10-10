package com.edutilos.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="husband")
@Getter
@Setter
public class IHusband {
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
    @OneToOne
    private IWife wife;

    public IHusband(long id, String name, int age, double wage, IWife wife) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.wage = wage;
        this.wife = wife;
    }

    public IHusband(String name, int age, double wage, IWife wife) {
        this.name = name;
        this.age = age;
        this.wage = wage;
        this.wife = wife;
    }

    public IHusband(long id, String name, int age, double wage) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.wage = wage;
    }

    public IHusband(String name, int age, double wage) {
        this.name = name;
        this.age = age;
        this.wage = wage;
    }

    public IHusband() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Husband(").append(id).append(",")
                .append(name).append(",")
                .append(age).append(",")
                .append(wage).append(") & Wife(")
                .append(getWife().getId()).append(",")
                .append(getWife().getName()).append(",")
                .append(getWife().getAge()).append(",")
                .append(getWife().getWage()).append(")");
        return sb.toString();
    }
}
