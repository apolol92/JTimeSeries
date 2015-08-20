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
 * Represents a single data point, consiting of one value t of independent variable and one value of a dependent
 * variable. Note that a data point can refer to a previously observed data point and also to the next observed
 * data point.
 */
public class DataPoint {
    /**
     * t is the independent variable. This value can be changed by calling setT.
     * To get a copy of this value use getT.
     *
     */
    private double t;
    /**
     * x is the dependent variable. This value can be changed by calling setX.
     * To get a copy of this value use getX.
     *
     */
    private double x;
    /**
     * Ths is a reference to the previous DataPoint in the time series.
     * Ths reference can be changed by calling setPrevDataPoint.
     * Use getPrevDataPoint to get the reference to the previous DataPoint.
     */
    private DataPoint prevDataPoint;
    /**
     * Ths is a reference to the next DataPoint in the time series.
     * Ths reference can be changed by calling setNextDataPoint.
     * Call getNextDataPoint to get the reference to the next DataPoint.
     */
    private DataPoint nextDataPoint;

    /**
     * Initializes the current DataPoint with the given value of the independent and dependent variable.
     * @param t the initial value of the independent variable.
     * @param x the initial value of the dependent variable.
     */
    public DataPoint(double t, double x) {
        this.t = t;
        this.x = x;
        this.prevDataPoint = null;
        this.nextDataPoint = null;
    }

    /**
     * Returns the current value assigned to the independent variable t.
     * @return the current value of the independent variable t.
     */
    public double getT() {
        return t;
    }

    /**
     * Sets the independent variable t to the given value
     * @param nt the new value of the independent variable t.
     */
    public void setT(double nt) {
        this.t = t;
    }

    /**
     * Returns the current value assigned to the dependent variable x.
     * @return the current value of the independent variable t.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the dependent variable x to the given value
     * @param nx the new value of t he dependent variable x.
     */
    public void setX(double nx) {
        this.x = x;
    }


    /**
     * Returns the current reference to the previous DataPoint in the time series.
     * @return the current reference to the previous DataPoint in the time series.
     */
    public DataPoint getPrevDataPoint() {
        return prevDataPoint;
    }

    /**
     * Sets a reference to a new previous DataPoint.
     * @param nPrevDataPoint the reference to the new prevDataPoint.
     */
    protected void setPrevDataPoint(DataPoint nPrevDataPoint) {
        this.prevDataPoint = prevDataPoint;
    }

    /**
     * Returns the current reference to the next DataPoint in the time series.
     * @return the current reference to the next DataPoint in the time series.
     */
    public DataPoint getNextDataPoint() {
        return nextDataPoint;
    }

    /**
     * Sets a reference to a new next DataPoint.
     * @param nNextDataPoint is the reference to the new nextDataPoint
     */
    protected void setNextDataPoint(DataPoint nNextDataPoint) {
        this.nextDataPoint = nextDataPoint;
    }

    /**
     * Calculate the gradient at the current DataPoint. It involves the nextDataPoint.
     * If there isn't any nextDataPoint, the method will use the prevDataPoint instead.
     * If there isn't any prevDataPoint, the method will return 0 (because only one DataPoint hasn't any gradient).
     * @return the gradient at the current DataPoint
     */
    double gradient() {
        //Trying to calculate the gradient with the nextDataPoint
        if((this.prevDataPoint==null & this.nextDataPoint!=null) || (this.prevDataPoint!=null & this.nextDataPoint!=null)) {
            return (this.nextDataPoint.x - this.x)/(this.nextDataPoint.t - this.t);
        }
        //If there isn't any nextDataPoint, we will use the prevDataPoint instead.
        else if(this.prevDataPoint!=null & this.nextDataPoint==null){
            return (this.x - this.prevDataPoint.x)/(this.t - this.prevDataPoint.t);
        }
        //If there isn't any prevDataPoint, we will return 0 (because only one DataPoint hasn't any gradient).
        else {
            return 0;
        }
    }

    /**
     * Calculates the euclidean distance between the current DataPoint and another DataPoint.
     * More informations about the euclidean distance: https://en.wikipedia.org/wiki/Euclidean_distance
     * @param other DataPoint
     * @return the euclidean distance
     */
    double euclideanDistance(DataPoint other) {
        return Math.sqrt(((this.t - other.t)*(this.t - other.t))+((this.x - other.x)*(this.x - other.x)));
    }

    /**
     * Calculate the euclidean distance between the current gradient of the DataPoint and the gradient of another
     * DataPoint.
     * More informations about the euclidean distance: https://en.wikipedia.org/wiki/Euclidean_distance
     * @param other DataPoint
     * @return the euclidean distance
     */
    double euclideanDistanceGradient(DataPoint other) {
        return Math.sqrt((this.gradient()-other.gradient())*(this.gradient()-other.gradient()));
    }


}
