package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    // to store current position
    static Long currentFrame;
    static Clip clip;

    // current status of clip
    static String status;

    static AudioInputStream audioInputStream;

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

    public static void read() throws IOException {
        AudioFormat format = audioInputStream.getFormat();

        int bytesPerFrame = format.getFrameSize();
        int sampleSizeInBits = format.getSampleSizeInBits();
        int channels = format.getChannels();
        boolean isBigEndian = format.isBigEndian();

        byte[] buffer = new byte[bytesPerFrame * 1024]; // Read 1024 frames at a time
        int bytesRead;

        while ((bytesRead = audioInputStream.read(buffer)) != -1) {
            for (int i = 0; i < bytesRead; i += bytesPerFrame) {
                // Process each frame
                for (int channel = 0; channel < channels; channel++) {
                    int sampleIndex = i + channel * (sampleSizeInBits / 8);

                    int sampleValue;
                    if (sampleSizeInBits == 16) {
                        if (isBigEndian) {
                            sampleValue = (buffer[sampleIndex] << 8) | (buffer[sampleIndex + 1] & 0xFF);
                        } else {
                            sampleValue = (buffer[sampleIndex + 1] << 8) | (buffer[sampleIndex] & 0xFF);
                        }
                    } else if (sampleSizeInBits == 8) {
                        sampleValue = buffer[sampleIndex]; // 8-bit is often unsigned
                    } else {
                        throw new UnsupportedOperationException("Unsupported sample size: " + sampleSizeInBits);
                    }

                    System.out.println("Channel " + channel + ": " + sampleValue);
                }
            }
        }
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
