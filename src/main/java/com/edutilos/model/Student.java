package com.edutilos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="student")
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;
    @Column(name="name")
    private String name;
    @Column(name="age")
    private int age;
    @Column(name="enrollmentNumber")
    private String enrollmentNumber;
    @ManyToOne
    private University university;

    public Student(long id, String name, int age, String enrollmentNumber) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.enrollmentNumber = enrollmentNumber;
    }

    public Student(String name, int age, String enrollmentNumber) {
        this.name = name;
        this.age = age;
        this.enrollmentNumber = enrollmentNumber;
    }

    public Student(long id, String name, int age, String enrollmentNumber, University university) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.enrollmentNumber = enrollmentNumber;
        this.university = university;
    }

    public Student(String name, int age, String enrollmentNumber, University university) {
        this.name = name;
        this.age = age;
        this.enrollmentNumber = enrollmentNumber;
        this.university = university;
    }

    public Student() {
    }

    @Override
    public String toString() {
        final String separator = ",";
        StringBuilder sb = new StringBuilder();
        sb.append("Student(").append(id).append(separator)
                .append(name).append(separator)
                .append(age).append(separator)
                .append(enrollmentNumber).append(") & University(")
                .append(getUniversity().getId()).append(separator)
                .append(getUniversity().getName()).append(separator)
                .append(getUniversity().getCountry()).append(separator)
                .append(getUniversity().getCity()).append(separator)
                .append(getUniversity().getPostcode()).append(")");
        return sb.toString();
    }

}
