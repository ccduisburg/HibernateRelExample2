package com.edutilos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="university")
@Getter
@Setter
public class University {
    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;
    @Column(name="name")
    private String name;
    @Column(name="country")
    private String country;
    @Column(name="city")
    private String city;
    @Column(name="postcode")
    private String postcode;
    @Column(name="rating")
    private double rating;
    @OneToMany(mappedBy = "university")
    private List<Student> students;
    @ManyToMany
    private List<Teacher> teachers;

    public University(long id, String name, String country, String city, String postcode, double rating, List<Student> students) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.postcode = postcode;
        this.rating = rating;
        this.students = students;
    }

    public University(String name, String country, String city, String postcode, double rating, List<Student> students) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.postcode = postcode;
        this.rating = rating;
        this.students = students;
    }

    public University(long id, String name, String country, String city, String postcode, double rating) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.postcode = postcode;
        this.rating = rating;
    }

    public University(String name, String country, String city, String postcode, double rating) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.postcode = postcode;
        this.rating = rating;
    }

    public University() {
    }

    @Override
    public String toString() {
        final String separator = ",";
        StringBuilder sb = new StringBuilder();
        sb.append("University(")
                .append(id).append(separator)
                .append(name).append(separator)
                .append(country).append(separator)
                .append(city).append(separator)
                .append(postcode).append(separator)
                .append(rating).append(")\n")
                .append("<<Students>>\n");
        getStudents().forEach(s-> sb.append(s).append("\n"));
        sb.append("\n<<Teachers>>\n");
        teachers.forEach(t->
                sb.append("Teacher(").append(t.getId()).append(separator)
                   .append(t.getName()).append(separator)
                   .append(t.getAge()).append(separator)
                   .append(t.getWage()).append(separator)
                   .append(String.join("; ", t.getSubjects())).append(")").append("\n"));
        return sb.toString();
    }
}
