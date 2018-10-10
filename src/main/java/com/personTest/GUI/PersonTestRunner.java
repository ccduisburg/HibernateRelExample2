package com.personTest.GUI;

import com.personTest.control.PersonTestController;
import com.personTest.dao._PersonDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PersonTestRunner extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        _PersonDAO dao = new _PersonDAO();
        FXMLLoader load=new FXMLLoader(getClass().getResource("/PersonTestMain.fxml"));
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PersonTestMain.fxml"));
            AnchorPane root = (AnchorPane)loader.load();
            loader.<PersonTestController>getController().loadPersonStudents(dao.findAll());
//            AnchorPane root=load.load();
            Scene scene=new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(Exception ex ){
            ex.printStackTrace();
        }

    }
}
