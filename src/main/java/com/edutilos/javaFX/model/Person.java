package com.edutilos.javaFX.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="javafx_person")
public class Person {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long Id;
    private String name;
    private Integer age;
    private Double wage;


}
