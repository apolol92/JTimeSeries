/*
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 */

package timeseries;

/**
 * Created by apolol92 on 20.08.15.
 * Represents a regulary time series. Regulary time series has got a constant spacing between the data points.
 * The standard spacing in this class is 1.
 * You can change the spacing by the initialization.
 */
public class RegularlyTimeSeries extends TimeSeries {
    /**
     * Tell you the spacing between 2 Data Points on the t-axes.
     * In a regulary time series the spacing is constant.
     */
    private final double spacing;

    /**
     * Initialize an empty RegularlytTimeSeries with default spacing 1
     */
    public RegularlyTimeSeries() {
        super();
        this.spacing = 1;
    }

    /**
     * Initialize an empty RegularlytTimeSeries with spacing = nSpacing
     * @param nSpacing specifics the spacing beetween every observation/DataPoint
     */
    public RegularlyTimeSeries(double nSpacing) {
        super();
        this.spacing = nSpacing;
    }

    /**
     * Initialize a RegularyTimeSeries with spacing = nSpacing and the first DataPoint(nt,nx)
     * @param nSpacing specifics the spacing beetween every observation/DataPoint
     * @param nt t value(independent value)
     * @param nx x value(dependent value)
     */
    public RegularlyTimeSeries(double nSpacing, double nt, double nx) {
        super();
        this.spacing = nSpacing;
        this.addFirstDataPoint(nt,nx);
    }

    /**
     * Gets the spacing between the ticks
     * @return spacing
     */
    public double getSpacing() {
        return spacing;
    }

    /**
     * Counts the amount of DataPoints
     * @return the amount of DataPoints
     */
    @Override
    public long size() {
        long i = 0;
        DataPoint walker = this.root;
        while(walker!=null) {
            walker = walker.getNextDataPoint();
            i++;
        }
        return i;
    }

    /**
     * If the regularly time series is empty, then use this method to add the first DataPoint.
     * @param nt t-value
     * @param nx x-value
     */
    public void addFirstDataPoint(double nt, double nx) {
        if(super.root==null) {
            super.root = new DataPoint(nt, nx);
            super.last = super.root;
        }
    }

    /**
     * Add a new DataPoint to the RegularlytTimeSeries.
     * It calculates the t-value by itself & only need the x-value.
     * If there was no DataPoint set till now, it automatically set the DataPoint t-value to 0.
     * @param nx x-value
     */
    public void addX(double nx) {
        if(super.root!=null) {
            super.last.setNextDataPoint(new DataPoint(super.last.getT() + this.spacing, nx));
            super.last = super.last.getNextDataPoint();
        } else {
            super.root = new DataPoint(0, nx);
            super.last = super.root;
        }
    }

    /**
     * Get the x-value at t
     * @param t position in time series
     * @return x-value at this position
     */
    public double getX(double t) {
        DataPoint walker = this.root;
        double currentT = this.root.getT();
        while(walker!=null) {
            if(currentT==t) {
                return walker.getX();
            }
            walker = walker.getNextDataPoint();
            currentT+=this.spacing;
        }
        return walker.getX();   //Throw Exception
    }


    /**
     * Creates a sub RegularyTimeSeries.
     * @param ft from t
     * @param tt to t (including this one)
     * @return
     */
    @Override
    public RegularlyTimeSeries subSeries(double ft, double tt) {
        RegularlyTimeSeries sub = new RegularlyTimeSeries(this.getSpacing());
        double t = 0;
        DataPoint walker = this.root;
        boolean firstElemenet = true;
        while(walker!=null) {
            if(t>=ft & t <= tt) {
                if(firstElemenet) {
                    sub.addFirstDataPoint(walker.getT(),walker.getX());
                    firstElemenet = false;
                }
                else {
                    sub.addX(walker.getX());
                }
            }
            t+=this.spacing;
            walker = walker.getNextDataPoint();
        }
        return sub;
    }

    /**
     * Creates a sub RegularyTimeSeries
     * @param fIndex from index
     * @param tIndex to index (including this one)
     * @return
     */

    @Override
    public RegularlyTimeSeries subSeries(long fIndex, long tIndex) {
        RegularlyTimeSeries sub = new RegularlyTimeSeries(this.getSpacing());
        long i = 0;
        DataPoint walker = this.root;
        boolean firstElement = true;
        while(walker!=null) {
            if(i>=fIndex & i <= tIndex) {
                if (firstElement) {
                    sub.addFirstDataPoint(walker.getT(),walker.getX());
                    firstElement = false;
                }
                else {
                    sub.addX(walker.getX());
                }
            }
            else if(i>tIndex) {
                break;
            }
            i++;
            walker = walker.getNextDataPoint();
        }
        return sub;
    }

    /**
     * Calculate a diverted time series of the original time series..
     * It can be used to remove trends from time series..
     * If you have got a linear trend, divert onetimes..
     * If you have got a quadratic trend, divert twotimes..
     * If you have got a n trend, divert n-times..
     * @return the diverted time series
     */
    @Override
    public RegularlyTimeSeries divert() {
        RegularlyTimeSeries divertion = new RegularlyTimeSeries(this.spacing);
        boolean firstElemenet = true;
        DataPoint walker = this.root;
        while(walker!=null) {
            if(firstElemenet) {
                divertion.addFirstDataPoint(walker.getT(),walker.gradient());
                firstElemenet = false;
            }
            else {
                divertion.addX(walker.gradient());
            }
            walker = walker.getNextDataPoint();
        }
        return divertion;
    }

}
