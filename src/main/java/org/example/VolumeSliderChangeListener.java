package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Duration;

import java.sql.Time;

/*
* This class serves to trigger volume adjustment when
* volume slider is moved
*/

public class VolumeSliderChangeListener implements ChangeListener<Number> {
    private long lastUpdate = 0;

    //throttled volume adjustment
    @Override
    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
        long now = System.currentTimeMillis();
        if(now-lastUpdate > 50){
            AudioPlayer.adjustVolume(newValue.floatValue());
            lastUpdate = now;
        }
    }
}
