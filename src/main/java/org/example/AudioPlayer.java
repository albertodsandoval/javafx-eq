package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    // to store current position
    Long currentFrame;
    static Clip clip;

    // current status of clip
    static String status;

    AudioInputStream audioInputStream;

    // file path
    static String filePath;

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
    }
    public static void play() {
        //start the clip
        clip.start();

        status = "play";
    }

    public static void setFilePath(String s){
        filePath = s;
    }

}
