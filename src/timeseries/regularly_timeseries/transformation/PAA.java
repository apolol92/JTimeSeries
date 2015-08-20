/*
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 */

package timeseries.regularly_timeseries.transformation;

import timeseries.RegularlyTimeSeries;

/**
 * Created by apolol92 on 20.08.15.
 * Piecewise Aggregate Approximation(PAA) use a non-overlapping window of length W.
 * It can be used for dimensionality reduction by replacing all DataPoints in the window with the window-mean.
 * It also can be used to approximate a time series.
 *
 */
public class PAA {
    /**
     * Defines the window size
     */
    private long windowSize;

    /**
     * Initialize a Piecewise Aggregate Approximation(PAA)
     * @param windowSize, the size of the window
     */
    public PAA(long windowSize) {
        this.windowSize = windowSize;
    }

    /**
     * Get the window size
     * @return the window size
     */
    public long getWindowSize() {
        return windowSize;
    }

    /**
     * Sets the window size
     * @param windowSize new window size
     */
    public void setWindowSize(long windowSize) {
        this.windowSize = windowSize;
    }

    /**
     * Reduce the dimension of the given time series
     * @param ts given time series
     * @return the reduced dimension time series
     */
    public RegularlyTimeSeries dimensionReduction(RegularlyTimeSeries ts) {
        long tsSize = ts.size();
        RegularlyTimeSeries reduced = new RegularlyTimeSeries(ts.getT(this.windowSize)-ts.getT(0));
        boolean firstElement = true;
        for(long i = 0; i < tsSize-this.windowSize; i=i+this.windowSize) {
            RegularlyTimeSeries sub = ts.subSeries(i, i + this.windowSize);
            double mean = sub.mean();
            if(firstElement) {
                reduced.addFirstDataPoint(ts.getT(i),mean);
            }
            else {
                reduced.addX(mean);
            }
        }
        return reduced;
    }

    /**
     * Approximate the given time series
     * @param ts given time series
     * @return approximation of the given time series
     */
    public RegularlyTimeSeries approximation(RegularlyTimeSeries ts) {
        long tsSize = ts.size();
        RegularlyTimeSeries approx = new RegularlyTimeSeries(ts.getSpacing());
        boolean firstElement = true;
        for(long i = 0; i < tsSize-this.windowSize+1; i++) {
            RegularlyTimeSeries sub = ts.subSeries(i, i + this.windowSize);
            double mean = sub.mean();
            if(firstElement) {
                approx.addFirstDataPoint(ts.getT(i), mean);
                firstElement = false;
            }
            else {
                approx.addX(mean);
            }
            if(i==tsSize-this.windowSize) {
                //Fill the rest
                i++;
                while(i<tsSize) {
                    if(firstElement) {
                        approx.addFirstDataPoint(ts.getT(i), mean);
                        firstElement = false;
                    }
                    else {
                        approx.addX(mean);
                    }
                    i++;
                }
            }
        }
        return  approx;
    }
}
