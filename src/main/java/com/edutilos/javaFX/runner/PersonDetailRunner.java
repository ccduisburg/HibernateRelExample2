package com.edutilos.javaFX.runner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PersonDetailRunner extends Application  {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PersonDetail.fxml"));
//        loader.setRoot(this);
//        loader.setController(this);
        try {
            AnchorPane root = (AnchorPane)loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
