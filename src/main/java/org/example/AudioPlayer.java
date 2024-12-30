package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    // to store current position
    static Long currentFrame;
    static Clip clip;

    // current status of clip
    static String status;

    AudioInputStream audioInputStream;

    // file path
    static String filePath;

    static FloatControl gainControl;

    // constructor to initialize streams and clip
    public AudioPlayer()
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        // create AudioInputStream object
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        // create clip reference
        clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);

        clip.loop(Clip.LOOP_CONTINUOUSLY);

        gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        gainControl.setValue(6.0206f);
    }

    public static void adjustVolume(Float newValue) {
        Float adjustedVolume = 6.0206f * (newValue/100);

        System.out.println(adjustedVolume);

        gainControl.setValue(adjustedVolume);
    }

    public void play() {
        //start the clip
        clip.start();

        status = "play";
    }

    public void setFilePath(String s){
        filePath = s;
    }

    public void pause() {
        if (status.equals("paused"))
        {
            System.out.println("audio is already paused");
            return;
        }
        currentFrame =
                clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    public void resume() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (status.equals("play"))
        {
            System.out.println("Audio is already "+
                    "being played");
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }

    private void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
