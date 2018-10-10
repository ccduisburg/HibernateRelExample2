package com.edutilos.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="person_transient")
public class PersonTransient {
    @Id
    @Column(name="id")
    private long id;
    private String fname;
    private String lname;
    @Transient
    private int age;
    @Transient
    private double wage;
    private boolean active;
    @ElementCollection
    private List<String> activities;
}
