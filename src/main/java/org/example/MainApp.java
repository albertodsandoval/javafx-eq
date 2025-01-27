package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage mainStage;

    //Start method inherited from Application
    //here is where you define your GUI
    @Override
    public void start(Stage stage) {
        mainStage = stage;

        //delegates creation of layout to MainView.java
        VBox vbox = MainView.getLayout();
        Scene scene = new Scene(vbox);
        stage.setTitle("10 Sliders");
        stage.setScene(scene);
        stage.setHeight(800);
        stage.setWidth(1000);

        stage.show();
    }

    //starts application
    public static void main(String[] args) {
        launch();
    }

    //returns the stage to be accessed in
    //other classes
    public static Stage getStage(){return mainStage;}
}