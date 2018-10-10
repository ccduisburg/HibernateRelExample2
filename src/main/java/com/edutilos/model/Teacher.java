package com.edutilos.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="teacher")
@Getter
@Setter
public class Teacher {
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
    @ElementCollection
    private List<String> subjects;
    @ManyToMany(mappedBy = "teachers")
    private List<University> universities;

    public Teacher(long id, String name, int age, double wage, List<String> subjects, List<University> universities) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.wage = wage;
        this.subjects = subjects;
        this.universities = universities;
    }

    public Teacher(String name, int age, double wage, List<String> subjects, List<University> universities) {
        this.name = name;
        this.age = age;
        this.wage = wage;
        this.subjects = subjects;
        this.universities = universities;
    }

    public Teacher(long id, String name, int age, double wage, List<String> subjects) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.wage = wage;
        this.subjects = subjects;
    }

    public Teacher(String name, int age, double wage, List<String> subjects) {
        this.name = name;
        this.age = age;
        this.wage = wage;
        this.subjects = subjects;
    }

    public Teacher() {
    }

    @Override
    public String toString() {
        final String separator = ",";
        StringBuilder sb = new StringBuilder();
        sb.append("Teacher(").append(id).append(separator)
                .append(name).append(separator)
                .append(age).append(separator)
                .append(wage).append(separator)
                .append(String.join(";")).append(")\n")
                .append("<<Universities>>").append("\n");
        universities.forEach(u->
                sb.append("University(").append(u.getId()).append(separator)
        .append(u.getName()).append(separator)
        .append(u.getCountry()).append(separator)
        .append(u.getCity()).append(separator)
        .append(u.getPostcode()).append(separator)
        .append(u.getRating()).append(")\n"));
        return sb.toString();
    }
}
