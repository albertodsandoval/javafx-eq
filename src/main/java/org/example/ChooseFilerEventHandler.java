package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class ChooseFilerEventHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choosing Audio File...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("AIFF Files","*.aiff"));

        File selectedFile = fileChooser.showOpenDialog(MainApp.getStage());

        MainView.setSelectedFile(selectedFile);


        try {
            ViewController.setAudioPlayer(new AudioPlayer());
            System.out.println("Break");
        } catch (UnsupportedAudioFileException
                 | IOException
                 | LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        ViewController.audioPlayer.setFilePath(selectedFile.getPath());
    }
}
