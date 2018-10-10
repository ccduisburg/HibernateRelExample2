package com.edutilos.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="wife")
@Getter
@Setter
public class IWife {
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
    private IHusband husband;


    public IWife(long id, String name, int age, double wage, IHusband husband) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.wage = wage;
        this.husband  = husband;
    }

    public IWife(String name, int age, double wage, IHusband husband) {
        this.name = name;
        this.age = age;
        this.wage = wage;
        this.husband = husband;
    }


    public IWife(long id, String name, int age, double wage) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.wage = wage;
    }

    public IWife(String name, int age, double wage) {
        this.name = name;
        this.age = age;
        this.wage = wage;
    }

    public IWife() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Wife(").append(id).append(",")
                .append(name).append(",")
                .append(age).append(",")
                .append(wage).append(") & Husband(")
                .append(getHusband().getId()).append(",")
                .append(getHusband().getName()).append(",")
                .append(getHusband().getAge()).append(",")
                .append(getHusband().getWage()).append(")");
        return sb.toString();
    }
}
