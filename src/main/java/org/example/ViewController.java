package org.example;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/*
* This class serves to hold methods that create
* all the controllers uses in our main view. This
* includes, the fields above the bands, the sliders
* representing the bands, the button to choose files,
* and the add button to add another band.
*/

public class ViewController {

    static AudioPlayer audioPlayer;

    //this method will create all the frequency fields
    //it takes an integer specifying how many you want
    public static void createFields(int num, GridPane gridPane) {
        //initializing frequency fields
        for(int i = 0; i < num; i++){
            TextField textField = new TextField(String.format("F%d",i+1));
            gridPane.add(textField,i,0);
            GridPane.setHalignment(textField, HPos.CENTER);
        }
    }

    //this method will create all the sliders
    //it takes an integer specifying how many you want
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

    //this method creates the choose file button
    public static Button createChooseFileButton() {


        //creating a button to choose files
        Button chooseFile = new Button("Choose File");

        //assigning chooseFile a designated eventHandler
        chooseFile.setOnAction(new ChooseFilerEventHandler());

        return chooseFile;
    }

    public static Button createPlayButton() {

        //creating play button
        Button playButton = new Button("▶");

        //sets the action of play button to be "play"
        //but then changes its behavior to "resume" after
        playButton.setOnAction(e -> {
            audioPlayer.play();
            playButton.setOnAction((ev->audioPlayer.resume()));
        });

        return playButton;

    }

    public static Button createPauseButton() {
        Button pauseButton = new Button("❚❚");
        pauseButton.setOnAction(e -> audioPlayer.pause());
        return pauseButton;
    }

    public static void setAudioPlayer(AudioPlayer audioPlayerToBeSet){
        audioPlayer = audioPlayerToBeSet;
    }

}