package org.example;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/*
* This class serves to hold methods that create
* all the controllers uses in our main view. This
* includes, the fields above the bands, the sliders
* representing the bands, the button to choose files,
* and the add button to add another band.
*/

public class ControllerService {
    public static void createFields(int num, GridPane gridPane) {
        //initializing frequency fields
        for(int i = 0; i < num; i++){
            TextField textField = new TextField(String.format("F%d",i+1));
            gridPane.add(textField,i,0);
            gridPane.setHalignment(textField, HPos.CENTER);
        }
    }

    public static void createSliders(int num, GridPane gridPane) {
        //initializing sliders
        for(int i = 0; i<num;i++){
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
            gridPane.add(slider,i,1);
            gridPane.setHalignment(slider,HPos.CENTER);
        }
    }

    public static Button createChooseFileButton() {
        //creating file extensions
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("FLAC Files","*.flac");
        FileChooser.ExtensionFilter ex2 = new FileChooser.ExtensionFilter("MP3 Files","*.mp3");

        //creating a button to choose files
        Button chooseFile = new Button("Choose File");

        //handling add file button click
        chooseFile.setOnAction(e -> promptChooseFile(ex1,ex2, MainApp.getStage()));

        return chooseFile;
    }

    private static void promptChooseFile(FileChooser.ExtensionFilter ex1, FileChooser.ExtensionFilter ex2, Stage stage) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choosing Audio File...");
        fileChooser.getExtensionFilters().addAll(ex1,ex2);

        File selectedFile = fileChooser.showOpenDialog(stage);
    }
}
