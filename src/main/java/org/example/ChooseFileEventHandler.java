package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;

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

        button.setText(selectedFile.getName());
    }
}
