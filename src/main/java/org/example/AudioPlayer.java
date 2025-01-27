package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;


public class AudioPlayer {

    // to store current position
    static SourceDataLine sourceDataLine;

    // current status of clip
    static String status;

    static AudioInputStream audioInputStream;

    // file path
    static String filePath;

    //used to control master volume
    static FloatControl gainControl;

    int bitDepth;

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

        if(format.getSampleSizeInBits()==8){
            bitDepth = 8;
        } else if (format.getSampleSizeInBits()==16){
            bitDepth = 16;
        } else {
            bitDepth = 0;
        }

        //specifies the type of data line, source data line with chosen format
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
        sourceDataLine.open(format);
        sourceDataLine.start();

        Thread playbackThread = getThread();
        playbackThread.start();
        
        gainControl = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(0f);
    }

    private Thread getThread() {
        byte[] buffer = new byte[1024];


        //atomic int used for thread safety
        final AtomicInteger bytesRead = new AtomicInteger(0);

        // Atomic update
        return new Thread(()->{
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

                switch (bitDepth){
                    case 0:{
                        throw new RuntimeException("only 8 bit and 16 bit depth is supported");
                    }

                    case 8:{
                        AudioDataConverter.preform8BitFFT(buffer);
                    }

                    case 16:{
                        AudioDataConverter.preform16BitFFT(buffer);
                    }
                }

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
        });
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
        float adjustedVolume = 86f * (sliderValue/120);
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
