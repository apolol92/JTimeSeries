/*
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 */

package timeseries.regularly_timeseries.evaluation;

import timeseries.RegularlyTimeSeries;

/**
 * Created by apolol92 on 22.08.15.
 */
public class Evaluator {

    public static double MeanAbsoluteError(RegularlyTimeSeries observations, RegularlyTimeSeries model) {
        double sum = 0;
        for(long i = 0; i < observations.size(); i++) {
            sum += Math.abs(observations.getX(i)-model.getX(i));
        }
        return sum/observations.size();
    }

    public static double MeanSquaredError(RegularlyTimeSeries observations, RegularlyTimeSeries model) {
        double sum = 0;
        for(long i = 0; i < observations.size(); i++) {
            sum += (observations.getX(i)-model.getX(i))*(observations.getX(i)-model.getX(i));
        }
        return sum/observations.size();
    }

    public static double RootMeanSquaredError(RegularlyTimeSeries observations, RegularlyTimeSeries model) {
        double sum = 0;
        System.out.println(observations.size()+" "+model.size());
        for(long i = 0; i < observations.size(); i++) {
            sum += (observations.getX(i)-model.getX(i))*(observations.getX(i)-model.getX(i));
        }
        return Math.sqrt(sum/observations.size());
    }
}
