package org.example;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.io.File;

public class MainView {

    //file object containing file selected by the
    //user, will probably be needed later
    private static File selectedFile;

    //returns the main vbox that defines the layout
    //to MainApp.java
    public static VBox getLayout(){
        //creating the layout
        VBox vbox = new VBox();

        //setting spacing
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(50));

        //creating new grid pane layout for
        //input fields above sliders and sliders
        GridPane midSection = new GridPane();
        midSection.getRowConstraints()
                .addAll(new RowConstraints(100), new RowConstraints(500));
        midSection.setHgap(15);//gap between sliders and boxes

        //creating 10 fields for band frequencies
        ViewController.createFields(10,midSection);

        //creating 10 sliders for bands
        ViewController.createSliders(10,midSection);

//        //add button to add a new slider
//        Button addButton = new Button("+");
//        midSection.add(addButton,midSection.getColumnCount(),1);

        //using methods in ViewController to create audio player buttons
        Button chooseFileButton = ViewController.createChooseFileButton();
        Button playButton = ViewController.createPlayButton();
        Button pauseButton = ViewController.createPauseButton();
        Slider volumeSlider = ViewController.createVolumeSlider();


        //creating an HBox containing both chooseFile button and play button
        HBox topSection = new HBox();
        topSection.getChildren().addAll(chooseFileButton,playButton,pauseButton, volumeSlider);
        topSection.setSpacing(25);

        //attached the choosefile button and gridpane
        //containing sliders and frequency fields to main vbox
        vbox.getChildren().addAll(topSection,midSection);

        //returns completed layout
        return vbox;
    }

    //allows us to set the File object in other classes
    public static void setSelectedFile(File file){
        selectedFile = file;
        AudioPlayer.filePath = file.getPath();
    }
}
