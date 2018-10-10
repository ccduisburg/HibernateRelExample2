package com.personTest.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "personstudent")
public class PersonStudent extends PersonTest {
    @Column
    private String scholl;
    @Column
    private Integer klasse;

    @ManyToOne (cascade = CascadeType.ALL)
    private Personschule schule;

    @Override
    public String toString() {
        final String separator = ",";
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("    ");
        sb.append("PersonStudent(").append(scholl).append(separator)
                .append(klasse).append(separator)
                .append(schule.getName()).append(")");
        return sb.toString();
    }

}
