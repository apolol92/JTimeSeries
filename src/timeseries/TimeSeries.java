/*
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 */

package timeseries;

import javax.xml.crypto.Data;

/**
 * Created by apolol92 on 20.08.15.
 * Represents a time series. A time series is a sequence of data points.
 * There are two types of time series:
 * - Regular time series: The spacing between every observation/data point is always the same
 * - Irregular time series: The spacing between every observation/data point is different
 * But these two types have much in common.
 * For this reason we use an abstract parent class called TimeSeries.
 */
public abstract class TimeSeries {
    /**
     * This is a reference on the root DataPoint of the time series
     */
    protected DataPoint root;
    /**
     * This is a reference on the latest DataPoint of the time series.
     * This reference often changes.
     */
    protected DataPoint last;

    /**
     * This standard constructor initialize the TimeSeries.
     */
    protected TimeSeries() {
        this.root = null;
        this.last = null;
    }

    /**
     * This method counts the DataPoints in the TimeSeries.
     * @return amount of DataPoints
     */
    public abstract long size();

    /**
     * This method measures the length of the TimeSeries.
     * Distance between the root DataPoint and the last DataPoint
     * @return
     */
    public double length() {
        return Math.sqrt((last.getT()-root.getT()*(last.getT()-root.getT())));
    }

    /**
     * This abstract method will be used to add Data(Points) to the TimeSeries
     * @param nx this value will be added as a new DataPoint
     */
    public abstract void addData(double nx);

    /**
     * This abstract method will be used to get DataPoints of the TimeSeries via the independent variable t
     * @param t get the DataPoint at t
     * @return DataPoint Reference
     */
    public abstract DataPoint getDataPoint(double t);

    /**
     * Gets the DataPoint at Index
     * @param index
     * @return DataPoint
     */
    public DataPoint getDataPoint(long index) {
        long i = 0;
        if(this.root!=null) {
            DataPoint walker = this.root;
            do {
                if(i==index) {
                    return walker;
                }
                i++;
            }while((walker=walker.getNextDataPoint())!=null);
            //index is out of bounds
            throw new IndexOutOfBoundsException();
        }
        throw new NullPointerException();       //Throw a NullPointerException
    }

    /**
     * This method will insert DataPoints via Index
     * @param index at this position the DataPoint will be inserted
     */
    public abstract void insertDataPoint(long index);

    /**
     * Calculates the max value of the time series
     * @return the max value
     */
    public double max() {
        DataPoint walker = this.root;
        double max = Double.MIN_VALUE;
        while(walker !=null) {
            if(walker.getX()>max) {
                max = walker.getX();
            }
            walker = walker.getNextDataPoint();
        }
        return max;
    }

    /**
     * Calculates the min value of the time series
     * @return the min value
     */
    public double min() {
        DataPoint walker = this.root;
        double min = Double.MAX_VALUE;
        while(walker !=null) {
            if(walker.getX()<min) {
                min = walker.getX();
            }
            walker = walker.getNextDataPoint();
        }
        return min;
    }

    /**
     * Calculates the mean value of the time series
     * @return
     */
    public double mean() {
        double mean = 0;
        double size = 0;
        if(this.root!=null) {
            //Time series contains elements
            DataPoint walker = this.root;
            do {
                mean += walker.getX();
                size++;
            } while ((walker = walker.getNextDataPoint()) != null);
            return mean/size;
        }
        return this.root.getX();    //Throw a NullPointerException
    }

    /**
     * Calculates the variance of the time series
     * @return the variance
     */
    public double variance() {
        double mean = this.mean();
        double v = 0;
        DataPoint walker = this.root;
        while(walker!=null) {
            v += (walker.getX()-mean)*(walker.getX()-mean);
            walker = walker.getNextDataPoint();
        }
        return v/this.size();
    }
    /**
     * Calculates the standard deviaton of the time series
     * @return the standard deviation
     */
    public double standardDeviation() {
        return Math.sqrt(this.variance());
    }

    /**
     * Calculates the median of the time series
     * @return the median
     */
    public double median() {
        return 0;
    }

    /**
     * Will create a sub time series of the current one.
     * This sub time series is totally independent from his parent
     * @param ft from t
     * @param tt to t (including this one)
     * @return a time series from t to t
     */
    public abstract TimeSeries subSeries(double ft, double tt);

    /**
     * Will create a sub time series of the current one.
     * This sub time series is totally independent from his parent
     * @param fIndex from index
     * @param tIndex to index (including this one)
     * @return a time series from index to index
     */
    public abstract TimeSeries subSeries(long fIndex, long tIndex);

    /**
     * This abstract method will remove a DataPoint from the time series via
     * @param t DataPoint at this position
     */
    public abstract void removeDataPoint(double t);

    /**
     * This abstract method will remove a DataPoint from the time series via
     * @param index DataPoint at this index
     */
    public abstract void removeDataPoint(long index);


}
