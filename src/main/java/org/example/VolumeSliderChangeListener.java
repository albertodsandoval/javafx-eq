package org.example;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.sound.sampled.FloatControl;

public class VolumeSliderChangeListener implements ChangeListener<Number> {
    @Override
    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
        System.out.println(newValue.getClass());

        AudioPlayer.adjustVolume(newValue.floatValue());
    }
}
