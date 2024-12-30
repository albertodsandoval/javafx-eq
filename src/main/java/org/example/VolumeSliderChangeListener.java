package org.example;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/*
* This class serves to trigger volume adjustment when
* volume slider is moved
*/

public class VolumeSliderChangeListener implements ChangeListener<Number> {
    @Override
    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
        System.out.println(newValue.getClass());

        //AudioPlayer method that changes volume
        AudioPlayer.adjustVolume(newValue.floatValue());
    }
}
