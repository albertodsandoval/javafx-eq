package org.example;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class MainView {
    private VBox vbox;
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



        vbox.getChildren().addAll(chooseFile,gridPane);
        return vbox;
    }
}
