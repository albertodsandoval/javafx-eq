package org.example;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.util.ArrayList;

public class PCMConverter {
    static byte[] buffer = new byte[1024];
    static int bytesRead;

    public static ArrayList<Double> convert() throws IOException {
        ArrayList<Double> pcmData = new ArrayList<>();
        while ((bytesRead = AudioPlayer.audioInputStream.read(buffer)) != -1) {
            double[] chunk = convertToPCM(buffer,bytesRead,AudioPlayer.audioInputStream.getFormat());
            for(double sample : chunk){
                pcmData.add(sample);
            }
        }
        return pcmData;
    }

    private static double[] convertToPCM(byte[] buffer, int bytesRead, AudioFormat format) {
        int sampleSizeInBits = format.getSampleSizeInBits(); // Get sample size
        int numChannels = format.getChannels(); // Get number of channels
        boolean isBigEndian = format.isBigEndian(); // Endianness of data
        int bytesPerSample = sampleSizeInBits / 8; // Bytes per sample (8-bit = 1 byte, 16-bit = 2 bytes, etc.)
        int numSamples = bytesRead / (bytesPerSample * numChannels); // Total number of samples (per channel)

        double[] pcmSamples = new double[numSamples * numChannels]; // Array to store PCM samples

        // Iterate over the buffer and extract samples for each channel
        for (int i = 0; i < numSamples; i++) {
            for (int ch = 0; ch < numChannels; ch++) {
                int sampleStart = (i * numChannels + ch) * bytesPerSample;
                int sample = 0;

                // Handle different endianness and sample sizes
                if (sampleSizeInBits == 8) {
                    sample = buffer[sampleStart]; // For 8-bit, just one byte per sample
                } else if (sampleSizeInBits == 16) {
                    if (isBigEndian) {
                        sample = (buffer[sampleStart] << 8) | (buffer[sampleStart + 1] & 0xFF);
                    } else {
                        sample = (buffer[sampleStart + 1] << 8) | (buffer[sampleStart] & 0xFF);
                    }
                } else if (sampleSizeInBits == 24) {
                    if (isBigEndian) {
                        sample = (buffer[sampleStart] << 16) | (buffer[sampleStart + 1] << 8) | (buffer[sampleStart + 2] & 0xFF);
                    } else {
                        sample = (buffer[sampleStart + 2] << 16) | (buffer[sampleStart + 1] << 8) | (buffer[sampleStart] & 0xFF);
                    }
                }

                // Normalize the sample to [-1, 1] (assuming signed PCM data)
                if (sampleSizeInBits == 8) {
                    pcmSamples[i * numChannels + ch] = (sample - 128) / 128.0; // Normalize 8-bit PCM
                } else if (sampleSizeInBits == 16) {
                    pcmSamples[i * numChannels + ch] = sample / 32768.0; // Normalize 16-bit PCM
                } else if (sampleSizeInBits == 24) {
                    pcmSamples[i * numChannels + ch] = sample / 8388608.0; // Normalize 24-bit PCM
                }
            }
        }

        return pcmSamples;

    }
}
