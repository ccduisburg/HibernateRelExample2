package com.edutilos.javaFX.controller;

import com.edutilos.javaFX.dao.PersonDAO;
import com.edutilos.javaFX.model.Person;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.List;

public class PersonDetailController {
    @FXML
    private TextField tfID, tfName, tfAge,tfWage;
    @FXML
    private Button btnPrint, btnClear,btnSave,btnUpdate,btnDelete,btnSearchbyID, btnSearchByName,btnPrev,btnNext;

    private PersonDAO prsDAO;

    @FXML
    private void initialize() {
        prsDAO = new PersonDAO();
       Person prs=new Person();


        btnPrint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                   Long ID= Long.parseLong(tfID.getText());
                    prs.setId(ID);
                    String name= tfName.getText();
                    prs.setName(name);
                    Integer age=Integer.parseInt(tfAge.getText());
                    prs.setAge(age);
                    Double wage=Double.parseDouble(tfWage.getText());
                    prs.setWage(wage);
                    System.out.println(ID +" "+name+" "+ age+" "+wage);
                }catch(Exception ex){
                    ex.printStackTrace();

                }

            }
        });

        btnClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tfID.setText("");
                tfName.setText("");
                tfAge.setText("");
                tfWage.setText("");
            }
        });

        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String name= tfName.getText();
                prs.setName(name);
                Integer age=Integer.parseInt(tfAge.getText());
                prs.setAge(age);
                Double wage=Double.parseDouble(tfWage.getText());
                prs.setWage(wage);
                prsDAO.create(prs);


            }
        });
        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Long ID= Long.parseLong(tfID.getText());
                prs.setId(ID);
                String name= tfName.getText();
                prs.setName(name);
                Integer age=Integer.parseInt(tfAge.getText());
                prs.setAge(age);
                Double wage=Double.parseDouble(tfWage.getText());
                prs.setWage(wage);
                prsDAO.update(ID.longValue(),prs);

            }
        });
        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                prsDAO.delete(Long.parseLong(tfID.getText()));

            }
        });

        btnSearchbyID.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Long ID= Long.parseLong(tfID.getText());

               Person p= (Person) prsDAO.find(ID);

                tfName.setText(p.getName());
                tfAge.setText(p.getAge().toString());
                tfWage.setText(p.getWage().toString());

            }
        });

        btnSearchByName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String name= tfName.getText();

                Person p= (Person) prsDAO.findByName(name);

                tfID.setText(p.getId().toString());
                tfAge.setText(p.getAge().toString());
                tfWage.setText(p.getWage().toString());

            }
        });
        List<Person> all=prsDAO.findAll();
        final int[] index={0};
        btnPrev.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (all.size()!=0){

                int i = index[0];
                i--;
                if(i<0)
                    i=all.size()-1;
                Person p=all.get(i);
                tfID.setText(p.getId().toString());
                tfName.setText(p.getName());
                tfAge.setText(p.getAge().toString());
                tfWage.setText(p.getWage().toString());
                index[0]=i;

            }
            }
        });

        btnNext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(all.size()!=0){
                int i = index[0];
                i++;
                if(i>=all.size()-1)
                    i=0;
                Person p=all.get(i);
                tfID.setText(p.getId().toString());
                tfName.setText(p.getName());
                tfAge.setText(p.getAge().toString());
                tfWage.setText(p.getWage().toString());
                index[0]=i;

            }
            }
        });

    }

}
