package org.example;


import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class AudioDataConverter {
    static FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);


    public static double[] convert8BitBytesToNormalizedDoubles(byte[] buffer) {
        double[] normalizedData = new double[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            // Normalize byte values (-128 to 127) to [-1.0, 1.0]
            normalizedData[i] = buffer[i] / 128.0;  // 128 is the max absolute value for 8-bit signed byte
        }

        return normalizedData;
    }

    public static double[] convert16BitBytesToNormalizedDoubles(byte[] buffer) {
        double[] normalizedData = new double[buffer.length / 2];  // Since each short is 2 bytes
        for (int i = 0; i < buffer.length; i += 2) {
            // Combine two bytes to form a short (16-bit value)
            short pcmValue = (short) ((buffer[i + 1] << 8) | (buffer[i] & 0xFF));  // Big-endian
            // Normalize short values (-32,768 to 32,767) to [-1.0, 1.0]
            normalizedData[i / 2] = pcmValue / 32767.0;  // 32767 is the max absolute value for 16-bit signed short
        }

        return normalizedData;
    }


    // Convert normalized time-domain data to 16-bit PCM byte data
    public static byte[] convertToByteData16Bit(double[] normalizedData) {
        int bitDepth = 16;  // Assuming 16-bit PCM audio
        int maxAmplitude = (1 << (bitDepth - 1)) - 1;  // Max value for 16-bit signed integer (32767)

        // Prepare an array to hold the byte data
        byte[] byteData = new byte[normalizedData.length * 2];  // 2 bytes per sample for 16-bit PCM

        // Create a ByteBuffer with little-endian order
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);  // Set byte order to little-endian

        // Convert each normalized sample to a 16-bit PCM byte pair
        for (int i = 0; i < normalizedData.length; i++) {
            // Scale the normalized sample to the range of 16-bit PCM
            short pcmValue = (short) (normalizedData[i] * maxAmplitude);

            // Clear the buffer and put the short value in little-endian byte order
            buffer.clear();
            buffer.putShort(pcmValue);

            // Get the bytes from the buffer and place them in the byte array
            byteData[i * 2] = buffer.get(0);  // Low byte
            byteData[i * 2 + 1] = buffer.get(1);  // High byte
        }

        return byteData;
    }


    // Convert normalized time-domain data to 8-bit PCM byte data
    public static byte[] convertToByteData8Bit(double[] normalizedData) {
        int bitDepth = 8;  // 8-bit PCM audio
        int maxAmplitude = (1 << (bitDepth - 1)) - 1;  // Max value for 8-bit signed integer (127)

        // Prepare an array to hold the byte data
        byte[] byteData = new byte[normalizedData.length];  // 1 byte per sample for 8-bit PCM

        // Convert each normalized sample to 8-bit PCM byte
        for (int i = 0; i < normalizedData.length; i++) {
            // Scale the normalized sample to the range of 8-bit PCM (-128 to 127)
            byte pcmValue = (byte) (normalizedData[i] * maxAmplitude);

            // Since it's 8-bit, there's no need for byte order adjustment, but we can use ByteBuffer if desired.
            // Just set the byte directly into the array
            byteData[i] = pcmValue;
        }

        return byteData;
    }

    public static void preform8BitFFT(byte[] buffer){
        double[] normalizedData = AudioDataConverter.convert8BitBytesToNormalizedDoubles(buffer);

        Complex[] frequencyDomain = transformer.transform(normalizedData, TransformType.FORWARD);

        Complex[] ifftResult = transformer.transform(frequencyDomain, TransformType.INVERSE);

        // Extract the real part of the IFFT result (time-domain signal)
        double[] timeDomainSignal = new double[ifftResult.length];
        for (int i = 0; i < ifftResult.length; i++) {
            timeDomainSignal[i] = ifftResult[i].getReal();  // Get the real part (since time-domain is real)
        }

        normalizedData = timeDomainSignal;
        byte[] byteData = AudioDataConverter.convertToByteData8Bit(normalizedData);

        // Replace the buffer with the processed data
        System.arraycopy(byteData, 0, buffer, 0, byteData.length);
    }

    public static void preform16BitFFT(byte[] buffer){
        double[] normalizedData = AudioDataConverter.convert16BitBytesToNormalizedDoubles(buffer);

        Complex[] frequencyDomain = transformer.transform(normalizedData, TransformType.FORWARD);

        //here is where you would preform the amplification or diminution of the bins

        Complex[] ifftResult = transformer.transform(frequencyDomain, TransformType.INVERSE);

        // Extract the real part of the IFFT result (time-domain signal)
        double[] timeDomainSignal = new double[ifftResult.length];
        for (int i = 0; i < ifftResult.length; i++) {
            timeDomainSignal[i] = ifftResult[i].getReal();  // Get the real part (since time-domain is real)
        }

        normalizedData = timeDomainSignal;

        byte[] byteData = AudioDataConverter.convertToByteData16Bit(normalizedData);

        // Find the min and max values manually
        byte min = Byte.MAX_VALUE;  // Start with the largest possible byte value
        byte max = Byte.MIN_VALUE;  // Start with the smallest possible byte value

        for (byte b : byteData) {
            if (b < min) min = b;
            if (b > max) max = b;
        }


        // Replace the buffer with the processed data
        System.arraycopy(byteData, 0, buffer, 0, byteData.length);
    }


}
