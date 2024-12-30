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

public class ControllerService {

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
        //creating file extensions
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("FLAC Files","*.aiff");
        FileChooser.ExtensionFilter ex2 = new FileChooser.ExtensionFilter("MP3 Files","*.mp3");
        FileChooser.ExtensionFilter ex3 = new FileChooser.ExtensionFilter("AIFF Files","*.aiff");


        //creating a button to choose files
        Button chooseFile = new Button("Choose File");

        //handling add file button click
        chooseFile.setOnAction(e -> promptChooseFile(ex1,ex2,ex3, MainApp.getStage()));

        return chooseFile;
    }

    //this method is the event handler of clicking the
    //choose file button. prompts user to choose a file
    private static void promptChooseFile(FileChooser.ExtensionFilter ex1, FileChooser.ExtensionFilter ex2, FileChooser.ExtensionFilter ex3, Stage stage) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choosing Audio File...");
        fileChooser.getExtensionFilters().addAll(ex1,ex2);

        File selectedFile = fileChooser.showOpenDialog(stage);

        MainView.setSelectedFile(selectedFile);

        AudioPlayer.setFilePath(selectedFile.getPath());
    }

    public static void initialPlay() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        AudioPlayer audioPlayer = new AudioPlayer();
        audioPlayer.play();
    }
}
