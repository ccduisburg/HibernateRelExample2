package com.personTest.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = "personschule")
public class Personschule implements Serializable {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Integer id;
    private String name;
    private String leite;

    @OneToMany
    private Set<PersonStudent> student;
//    @OneToOne
//    private PersonStudent student;

}

