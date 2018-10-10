package com.personTest.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;



@Entity
@Getter
@Setter
@Table(name = "personadrestest")
public class Personadress implements Serializable {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Integer id;
    private String strasse;
    private String PLZ;
    private Integer hnummer;
    @OneToOne
    private PersonStudent personStudent;


    public Personadress() {

    }


    public Personadress(Integer id, String strasse, String PLZ, Integer hnummer) {
        this.id = id;
        this.strasse = strasse;
        this.PLZ = PLZ;
        this.hnummer = hnummer;
    }


    @Override
    public String toString() {
        return String.format("Personadress(%d,%s,%s,%d)", id, strasse, PLZ, hnummer);
    }
}


