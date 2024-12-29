package org.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainApp extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage stage) {
        this.mainStage = stage;

        //delegates creation of layout to MainView.java
        VBox vbox = MainView.getLayout();
        Scene scene = new Scene(vbox);
        stage.setTitle("10 Sliders");
        stage.setScene(scene);
        stage.setHeight(800);
        stage.setWidth(1000);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getStage(){return mainStage;}
}