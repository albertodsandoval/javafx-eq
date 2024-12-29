package org.example;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.io.File;

public class MainView {

    //main layout which will contain all other
    //controllers
    private VBox vbox;

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

        //creating new hbox layout for frequency
        //input fields above sliders
        GridPane gridPane = new GridPane();
        gridPane.getRowConstraints().addAll(new RowConstraints(100), new RowConstraints(500));
        gridPane.setHgap(15);//gap between sliders and boxes

        //creating 10 fields for band frequencies
        ControllerService.createFields(10,gridPane);

        //creating 10 sliders for bands
        ControllerService.createSliders(10,gridPane);


        //add button to add a new slider
        Button addButton = new Button("+");
        gridPane.add(addButton,gridPane.getColumnCount(),1);

        //creating the choose file button
        Button chooseFile = ControllerService.createChooseFileButton();

        //attached the choosefile button and gridpane
        //containing sliders and frequency fields to main vbox
        vbox.getChildren().addAll(chooseFile,gridPane);

        //returns completed layout
        return vbox;
    }

    //allows us to set the File object in other classes
    public static void setSelectedFile(File file){
        selectedFile = file;
    }

    //allows us to utilize the selected File object in
    //other classes
    public static File getSelectedFile(File file){
        return selectedFile;
    }
}
