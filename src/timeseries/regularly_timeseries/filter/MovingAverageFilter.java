/*
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 */

package timeseries.regularly_timeseries.filter;

import timeseries.RegularlyTimeSeries;

/**
 * Created by apolol92 on 20.08.15.
 * The moving average filter removes the high frequencies..
 */
public class MovingAverageFilter {
    /**
     * The size of the filter window
     */
    private long windowSize;

    public MovingAverageFilter(long windowSize) {
        this.windowSize = windowSize;
    }

    /**
     * Getter method for windowSize
     * @return window size
     */
    public long getWindowSize() {
        return windowSize;
    }

    /**
     * Setter method for windowSize
     * @param windowSize
     */
    public void setWindowSize(long windowSize) {
        this.windowSize = windowSize;
    }

    /**
     * Filter the time series
     * @param ts
     * @return filtered time series
     */
    public RegularlyTimeSeries sFilter(RegularlyTimeSeries ts) {
        long tsSize = ts.size();
        boolean firstElement = true;
        RegularlyTimeSeries filteredTs = new RegularlyTimeSeries(ts.getSpacing());
        for(long i = 0; i < tsSize-this.windowSize+1; i++) {
            RegularlyTimeSeries sub = ts.subSeries(i, i + this.windowSize);
            double mean = sub.mean();
            filteredTs.addFirstDataPoint(ts.getT(0), mean);
            if(firstElement) {
                filteredTs.addFirstDataPoint(ts.getT(i),mean);
            }
            else {
                filteredTs.addX(mean);
            }
            if(i==tsSize-this.windowSize) {
                //Fill the rest
                i++;
                while(i<tsSize) {
                    if(firstElement) {
                        filteredTs.addFirstDataPoint(ts.getT(i),mean);
                    }
                    else {
                        filteredTs.addX(mean);
                    }
                    i++;
                }
            }
        }
        return filteredTs;
    }
}
