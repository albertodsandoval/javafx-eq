package org.example;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        HBox layout = new HBox();
        layout.setSpacing(10);
        Insets Insets = new Insets(50);
        layout.setPadding(Insets);
        for(int i = 0; i<11;i++){
            Slider slider = new Slider();
            slider.setOrientation(Orientation.VERTICAL);
            slider.setMax(10);
            slider.setMin(-10);
            slider.setValue(0);
            slider.setMajorTickUnit(1);
            slider.setBlockIncrement(0.5f);
            slider.setShowTickMarks(true);
            slider.setShowTickLabels(true);
            slider.setSnapToTicks(true);
            layout.getChildren().add(slider);
        }
        Scene scene = new Scene(layout);
        stage.setTitle("10 Sliders");
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(700);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}