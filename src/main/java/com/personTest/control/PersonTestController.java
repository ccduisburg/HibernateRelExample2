package com.personTest.control;

import com.edutilos.javaFX.model.Person;
import com.edutilos.test.AutoCompleteTextField;
import com.personTest.dao._PersonDAO;
import com.personTest.model.PersonStudent;
import com.personTest.model.PersonTest;
import com.personTest.model.Personadress;
import com.personTest.model.Personschule;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.scene.BoundsAccessor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersonTestController {
    @FXML
    private AutoCompleteTextField txtVorname,txtStrasse,txtHnummer,txtPLZ,txtSchule,txtLeite,txtKlasse;
    @FXML
    private AutoCompleteTextField txtName;

    @FXML
    private Button btnPrev,btnClear,btnSave,btnUpdate,btnDelete,btnNext,btnSearchByName;
    @FXML
    private ComboBox cboxSchule;
    @FXML
    private Label lblStudentID;
    @FXML
    private TableView<PersonStudent> tblvSchule;
    @FXML
    private GridPane gridStudentList;

    private _PersonDAO persTDAO;
    List<PersonStudent> allStudent = new ArrayList<>();


    public void loadPersonStudents(List<PersonStudent> students) {
        tblvSchule.getItems().clear();
        tblvSchule.getItems().addAll(students);
    }

    public void loadPersonStudents() {
        tblvSchule.getItems().clear();
        tblvSchule.getItems().addAll(persTDAO.findAll());
    }

    private void clearField() {
        txtName.setText("");
        txtVorname.setText("");
        txtStrasse.setText("");
        txtHnummer.setText("");
        txtPLZ.setText("");
        txtSchule.setText("");
        txtLeite.setText("");
        txtKlasse.setText("");
        lblStudentID.setText("...");

    }

    private void refreshStudentList() {
        allStudent.clear();
        allStudent.addAll(persTDAO.findAll());
    }

    private void assignAutomCompleteTextFields() {
        txtName.setFieldName("name");
        txtName.setTxtVorname(txtVorname);
        txtName.setTxtStrasse(txtStrasse);
        txtName.setTxtHnummer(txtHnummer);
        txtName.setTxtPLZ(txtPLZ);
        txtName.setTxtSchule(txtSchule);
        txtName.setTxtLeite(txtLeite);
        txtName.setTxtKlasse(txtKlasse);
//        txtName.setDao(persTDAO);
    }



    @FXML
    private void initialize() {

        persTDAO = new _PersonDAO();
        PersonTest prs = new PersonTest();
        Personadress personadress = new Personadress();
        PersonStudent personStudent = new PersonStudent();
        Personschule personschule = new Personschule();
        refreshStudentList();
        final int[] index = {0};
        txtName.getEntries().clear();
        txtName.getEntries().addAll(persTDAO.findAll().stream().map(student-> student.getName()).collect(Collectors.toList()));
        assignAutomCompleteTextFields();
//        loadPersonStudents(allStudent);

        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String name = txtName.getText();
                    personStudent.setName(name);
                    String vorname = txtVorname.getText();
                    personStudent.setVorname(vorname);
                    String strasse = txtStrasse.getText();
                    Integer klasse= Integer.parseInt(txtKlasse.getText());
                    personStudent.setKlasse(klasse);
                    personadress.setStrasse(strasse);
                    String PLZ = txtPLZ.getText();
                    personadress.setPLZ(PLZ);
                    String hnummer = txtHnummer.getText();
                    personadress.setHnummer(Integer.parseInt(hnummer));

                    personStudent.setAdress(personadress);
                    personadress.setPersonStudent(personStudent);


                    String schule = txtSchule.getText();
                    personschule.setName(schule);
                    String leiter = txtLeite.getText();
                    personschule.setLeite(leiter);
                    personStudent.setScholl(schule);
                    personStudent.setSchule(personschule);
                    personschule.setStudent(Stream.of(personStudent).collect(Collectors.toSet()));
                    persTDAO.create(personStudent);
                    allStudent.add(personStudent);
                    clearField();
                    refreshStudentList();
                    loadPersonStudents();
                    txtName.getEntries().addAll(persTDAO.findAll().stream().map(student-> student.getName()).collect(Collectors.toList()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        btnPrev.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(allStudent.size()!=0){
                int i = index[0];
                i--;
                if (i < 0)
                    i = allStudent.size() - 1;
                PersonStudent p = allStudent.get(i);
                lblStudentID.setText(p.getId().toString());
                txtName.setText(p.getName());
                txtVorname.setText(p.getVorname());
                txtStrasse.setText(p.getAdress().getStrasse());
                txtHnummer.setText(p.getAdress().getHnummer().toString());
                txtPLZ.setText(p.getAdress().getPLZ());
                txtSchule.setText(p.getSchule().getName());
                txtKlasse.setText(p.getKlasse().toString());
                txtLeite.setText(p.getSchule().getLeite());
                index[0] = i;

            }}
        });
        btnNext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(allStudent.size()!=0){
                int i = index[0];
                i++;
                if (i >=allStudent.size())
                    i = 0;
                PersonStudent p = allStudent.get(i);
                lblStudentID.setText(p.getId().toString());
                txtName.setText(p.getName());
                txtVorname.setText(p.getVorname());
                txtStrasse.setText(p.getAdress().getStrasse());
                txtHnummer.setText(p.getAdress().getHnummer().toString());
                txtPLZ.setText(p.getAdress().getPLZ());
                txtSchule.setText(p.getSchule().getName());
                txtKlasse.setText(p.getKlasse().toString());
                txtLeite.setText(p.getSchule().getLeite());
                index[0] = i;

            }}
        });

        btnClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearField();
            }
        });
        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                persTDAO.delete(Integer.parseInt(lblStudentID.getText()));
//                allStudent.removeIf(personStudent1 -> lblStudentID.getText().equals(personStudent1.getId()));
                allStudent.clear();
                allStudent.addAll(persTDAO.findAll());
                clearField();
                refreshStudentList();
                loadPersonStudents();
            }
        });

        btnSearchByName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String name = txtName.getText();
                PersonStudent p = (PersonStudent) persTDAO.findByName(name);
                lblStudentID.setText(p.getId().toString());
                txtName.setText(p.getName());
                txtVorname.setText(p.getVorname());
                txtStrasse.setText(p.getAdress().getStrasse());
                txtHnummer.setText(p.getAdress().getHnummer().toString());
                txtPLZ.setText(p.getAdress().getPLZ());
                txtSchule.setText(p.getSchule().getName());
                txtKlasse.setText(p.getKlasse().toString());
                txtLeite.setText(p.getSchule().getLeite());

            }
        });

        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Integer ID = Integer.parseInt(lblStudentID.getText());

                try {
                    PersonStudent personStudent = new PersonStudent();
                    String name = txtName.getText();
                    personStudent.setName(name);
                    String vorname = txtVorname.getText();
                    personStudent.setVorname(vorname);
                    String strasse = txtStrasse.getText();
                    personadress.setStrasse(strasse);
                    String PLZ = txtPLZ.getText();
                    personadress.setPLZ(PLZ);
                    String hnummer = txtHnummer.getText();
                    personadress.setHnummer(Integer.parseInt(hnummer));
                    personStudent.setAdress(personadress);
                    String schule = txtSchule.getText();
                    personschule.setName(schule);
                    String leiter = txtLeite.getText();
                    personschule.setLeite(leiter);
                    personStudent.setScholl(schule);
                    personStudent.setSchule(personschule);
                    persTDAO.update(ID, personStudent);
//                    allStudent.set(ID,personStudent);
                    clearField();
                    refreshStudentList();
                    loadPersonStudents();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });



//        txtName.setOnKeyTyped(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                String word = txtName.getText() + event.getCharacter();
//                System.out.println(word);
////                try {
////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//                PersonStudent personStudent = persTDAO.findByName(word);
//                if(personStudent != null) {
//                    populateFields("name", word,personStudent);
//                } else {
//                    clearField();
//                }
//            }
//        });
//
        txtName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String word = txtName.getText(); // + event.getCharacter();
                System.out.println(word);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                PersonStudent personStudent = persTDAO.findByName(word);
                if(personStudent != null) {
                    populateFields("name",personStudent);
                } else {
                    clearField();
                }
            }
        });


    }

    private void populateFields(String fieldName, PersonStudent personStudent) {
//        clearField();
        switch(fieldName) {
            case "name":
                txtVorname.setText(personStudent.getVorname());
                txtStrasse.setText(personStudent.getAdress().getStrasse());
                txtHnummer.setText(personStudent.getAdress().getHnummer()+"");
                txtPLZ.setText(personStudent.getAdress().getPLZ());
                txtSchule.setText(personStudent.getSchule().getName());
                txtLeite.setText(personStudent.getSchule().getLeite());
                txtKlasse.setText(personStudent.getKlasse()+"");
                break;
            default:
                txtName.setText(personStudent.getName());
                txtVorname.setText(personStudent.getVorname());
                txtStrasse.setText(personStudent.getAdress().getStrasse());
                txtHnummer.setText(personStudent.getAdress().getHnummer()+"");
                txtPLZ.setText(personStudent.getAdress().getPLZ());
                txtSchule.setText(personStudent.getSchule().getName());
                txtLeite.setText(personStudent.getSchule().getLeite());
                txtKlasse.setText(personStudent.getKlasse()+"");

        }

    }


}
