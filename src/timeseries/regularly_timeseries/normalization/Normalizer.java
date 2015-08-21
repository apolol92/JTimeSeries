/*
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 */

package timeseries.regularly_timeseries.normalization;


import timeseries.RegularlyTimeSeries;

/**
 * Created by apolol92 on 20.08.15.
 * Normalize can be used for better time series comparison..
 */
public class Normalizer {

    /**
     * Calculates the min/max-normalization
     * @param ts which time series should be normalized?
     * @return normalized time series
     */
    public static RegularlyTimeSeries minMaxNormalization(RegularlyTimeSeries ts) {
        double min = ts.min();
        double max = ts.max();
        long size = ts.size();
        RegularlyTimeSeries nTimeSeries = new RegularlyTimeSeries(ts.getSpacing());
        nTimeSeries.addFirstDataPoint(ts.getT(0),(ts.getX(0)-min)/(max-min));
        /**
         * Bad performance..
         */
        for(long i = 1; i < size; i++) {
            nTimeSeries.addX((ts.getX(i)-min)/(max-min));
        }
        return  nTimeSeries;
    }

    /**
     * Calculate the z-score normalization
     * @param ts which time series should be normalized?
     * @return normalized time series
     */
    public static RegularlyTimeSeries zScoreNormalization(RegularlyTimeSeries ts) {
        double mean = ts.mean();
        double stddev = ts.standardDeviation();
        long size = ts.size();
        RegularlyTimeSeries nTimeSeries = new RegularlyTimeSeries(ts.getSpacing());
        nTimeSeries.addFirstDataPoint(ts.getT(0),(ts.getX(0)-mean)/stddev);
        /**
         * Bad performance..
         */
        for(long i = 1; i < size; i++) {
            nTimeSeries.addX((ts.getX(i) - mean) / stddev);
        }
        return  nTimeSeries;
    }
}
