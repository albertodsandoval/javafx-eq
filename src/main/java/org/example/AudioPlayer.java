package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.pow;


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

    static double frequency;

    // constructor to initialize streams and clip
    public AudioPlayer()
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException, InterruptedException {

        //initialized to paused, user must play
        isPaused = true;

        audioInputStream =
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        AudioFormat format = audioInputStream.getFormat();

        frequency = format.getSampleRate();

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

    public static void adjustVolume(Float sliderValue) {
        float adjustedVolume = sliderValue - 100;
        if(adjustedVolume < 0) {
            adjustedVolume = (float) (20 * Math.log10(Math.abs(adjustedVolume)));
            adjustedVolume *= -1;
        } else {
            adjustedVolume = (float) (20 * Math.log10(adjustedVolume));
        }
        System.out.println(adjustedVolume);

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
