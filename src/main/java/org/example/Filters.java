package org.example;

import org.apache.commons.math3.complex.Complex;

public class Filters {
    public static Complex[] lowPassFilter(Complex[] fourierTransform, double lowPass, double frequency){
        double[] frequencyDomain = new double[fourierTransform.length];
        for(int i = 0; i < frequencyDomain.length; i++)
            frequencyDomain[i] = (frequency * i) / (double) fourierTransform.length;

        double[] keepPoints = new double[frequencyDomain.length];
        for (int i = 0; i < frequencyDomain.length; i++) {
            keepPoints[i] = (frequencyDomain[i] < lowPass) ? 1.0 : 0.0;
        }

        for (int i = 0; i < fourierTransform.length; i++)
            fourierTransform[i] = fourierTransform[i].multiply(keepPoints[i]);


        return fourierTransform;
    }
}
