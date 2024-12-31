package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class ChooseFileEventHandler implements EventHandler<ActionEvent> {
    private Button button;
    public ChooseFileEventHandler(Button button) {
        this.button = button;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choosing Audio File...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("AIFF Files","*.aiff"));

        File selectedFile = fileChooser.showOpenDialog(MainApp.getStage());

        MainView.setSelectedFile(selectedFile);

        AudioPlayer.filePath = selectedFile.getPath();

        try {
            AudioPlayer.audioInputStream = AudioSystem.getAudioInputStream(new File(AudioPlayer.filePath).getAbsoluteFile());
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }

        button.setText(selectedFile.getName());
    }
}
