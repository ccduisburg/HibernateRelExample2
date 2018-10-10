package com.edutilos.test;
import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import java.util.Arrays;
import javafx.stage.Stage;
public class Runner extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AutoCompleteTextField tf = new AutoCompleteTextField();
        tf.getEntries().addAll(Arrays.asList("foo", "bar", "bim", "pako"));
        AnchorPane root = new AnchorPane();
        root.getChildren().add(tf);
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}