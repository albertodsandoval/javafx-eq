package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.sound.sampled.*;
import javax.xml.transform.Source;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class AudioPlayer {

    // to store current position
    static Long currentFrame;
    static SourceDataLine sourceDataLine;

    // current status of clip
    static String status;

    static AudioInputStream audioInputStream;

    // file path
    static String filePath;

    //used to control master volume
    static FloatControl gainControl;

    //specifies status
    volatile static boolean isPaused;

    // constructor to initialize streams and clip
    public AudioPlayer()
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException, InterruptedException {

        //initialized to paused, user must play
        isPaused = true;

        audioInputStream =
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        AudioFormat format = audioInputStream.getFormat();

        //specifies the type of data line, source data line with chosen format
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        System.out.println("Data line info caught");


        // create clip reference
         sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);

        // open audioInputStream to the clip
        sourceDataLine.open(format);

        System.out.println("Data line opened");


        sourceDataLine.start();

        System.out.println("Data line started");

        byte[] buffer = new byte[1024];

        final AtomicInteger bytesRead = new AtomicInteger(0);

        System.out.println("Atomic int created");


        Thread playbackThread = new Thread(()->{
            while(true) {
                int currentBytesRead;
                try {
                    currentBytesRead = audioInputStream.read(buffer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (currentBytesRead == -1) {
                    break;
                }

                System.out.println(currentBytesRead);

                synchronized (this){
                    while (isPaused){
                        System.out.println("waiting");
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                bytesRead.set(currentBytesRead); // Atomic update

                sourceDataLine.write(buffer,0,currentBytesRead);
            }
            System.out.println("While terminated");

        });

        System.out.println("Thread created");

        playbackThread.start();

        System.out.println("Thread started");

        System.out.println("Thread NOT joined LOL");

        gainControl = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);

        gainControl.setValue(0f);
    }

    //methods that transcribes the value from the slider
    //into a reasonable float value input
    //slider has a max value of 120, 20 of its units
    //go over the default volume range
    //the range of float values that can represent values is
    //-80 - 6, which makes the length of the interval
    //86. if we take the percent value of the slider
    //aka slidervalue/120, and multiply it by the 86 float
    //value, we get a float that represents a value in the interval
    //to center the value, we subtract 80f
    public static void adjustVolume(Float sliderValue) {
        Float adjustedVolume = 86f * (sliderValue/120);
        adjustedVolume -= 80f;
        gainControl.setValue(adjustedVolume);
    }


    public void play() {

        synchronized (this){
            isPaused = false;

            //pings the playbackThread
            notifyAll();
        }

        status = "play";

    }

    public void pause() {

        synchronized (this){
            isPaused = true;

            //pings the playbackThread
            notifyAll();
        }

        status = "paused";

    }
}
