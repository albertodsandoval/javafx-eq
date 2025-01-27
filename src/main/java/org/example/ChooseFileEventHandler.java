package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class ChooseFileEventHandler implements EventHandler<ActionEvent> {
    private final Button button;
    public ChooseFileEventHandler(Button button) {
        this.button = button;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choosing Audio File...");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("AIFF Files","*.aiff"),new FileChooser.ExtensionFilter("WAV Files","*.wav"));

        File selectedFile = fileChooser.showOpenDialog(MainApp.getStage());

        MainView.setSelectedFile(selectedFile);

        AudioPlayer.filePath = selectedFile.getPath();

        System.out.println("Break 1");

        try {
            AudioPlayer.audioInputStream = AudioSystem.getAudioInputStream(new File(AudioPlayer.filePath).getAbsoluteFile());
            System.out.println("Break 2");

        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Break 3");

        try {
            System.out.println("Break 4");
            ViewController.audioPlayer = new AudioPlayer();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException |
                 InterruptedException exception) {
            exception.printStackTrace();
        }
        System.out.println("Break 5");

        button.setText(selectedFile.getName());
    }
}
