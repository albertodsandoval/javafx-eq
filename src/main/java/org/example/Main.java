package org.example;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // creating group
        Group root = new Group();
        Scene scene = new Scene(root, 600, 400);

        // set Scene to the stage
        stage.setScene(scene);

        // set title for the frame
        stage.setTitle("Slider Sample");

        // create slider
        Slider slider = new Slider();

        // add slider to the frame
        root.getChildren().add(slider);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}