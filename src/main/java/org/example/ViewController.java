package org.example;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


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
            GridPane.setHalignment(slider,HPos.CENTER);
        }
    }

    //this method creates the choose file button
    public static Button createChooseFileButton() {


        //creating a button to choose files
        Button chooseFile = new Button("Choose File");
        chooseFile.maxWidth(50);
        chooseFile.prefWidth(50);

        //assigning chooseFile a designated eventHandler
        chooseFile.setOnAction(new ChooseFileEventHandler(chooseFile));

        return chooseFile;
    }

    public static Button createPlayButton() {

        //creating play button
        Button playButton = new Button("▶");

        //sets the action of play button to be "play"
        //but then changes its behavior to "resume" after
        playButton.setOnAction(_ -> {
            System.out.println("Playing audio");
            audioPlayer.play();
        });

        return playButton;

    }

    public static Button createPauseButton() {
        Button pauseButton = new Button("❚❚");
        pauseButton.setOnAction(_ -> {
            audioPlayer.pause();
            System.out.println("Pausing audio");
        });
        return pauseButton;
    }

    //creates a volume slider with the appropriate features
    //returns the slide
    public static Slider createVolumeSlider() {
        Slider volumeSlider = new Slider();

        volumeSlider.setMax(120);
        volumeSlider.setMin(0);
        volumeSlider.setValue(100);
        volumeSlider.setBlockIncrement(0.1f);
        volumeSlider.setMajorTickUnit(10);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setPrefWidth(300);

        //adds dedicated change listener
        volumeSlider.valueProperty().addListener(new VolumeSliderChangeListener());

        return volumeSlider;
    }
}
