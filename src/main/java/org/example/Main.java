package org.example;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Group root = new Group();

        // create a Scene
        Scene scene = new Scene(root, 600, 400);

        // add Scene to the frame
        stage.setScene(scene);

        // set title of the frame
        stage.setTitle("Slider Sample");

        // Creates a slider
        Slider slider = new Slider(-10, 10, 0);

        // enable the marks
        slider.setShowTickMarks(true);

        // enable the Labels
        slider.setShowTickLabels(true);

        // set Major tick unit
        slider.setMajorTickUnit(1);

        // sets the value of the property
        // blockIncrement
        slider.setBlockIncrement(0.5f);

        root.getChildren().add(slider);

        // display
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}