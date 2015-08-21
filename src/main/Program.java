/*
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 */

package main;

import com.sun.org.apache.xpath.internal.operations.Mult;
import timeseries.RegularlyTimeSeries;
import timeseries.regularly_timeseries.evaluation.Evaluator;
import timeseries.regularly_timeseries.forecasters.exponential_smoothing.*;

/**
 * Created by apolol92 on 20.08.15.
 * Just the Application class for tests..
 */
public class Program {
    public static void main(String args[]) {
        RegularlyTimeSeries ts = new RegularlyTimeSeries(1,1996,446.7);
        ts.addX(454.5);
        ts.addX(455.7);
        ts.addX(423.6);
        ts.addX(456.3);
        ts.addX(440.6);
        ts.addX(425.3);
        ts.addX(485.1);
        ts.addX(506.0);
        ts.addX(526.8);
        ts.addX(514.3);
        ts.addX(494.2);
        //System.out.println(tsSes);
        RegularlyTimeSeries ts2 = new RegularlyTimeSeries(1,1990,17.55);
        ts2.addX(21.86);
        ts2.addX(23.89);
        ts2.addX(26.93);
        ts2.addX(26.89);
        ts2.addX(28.83);
        ts2.addX(30.08);
        ts2.addX(30.95);
        ts2.addX(30.19);
        ts2.addX(31.58);
        ts2.addX(32.58);
        ts2.addX(33.48);
        ts2.addX(39.02);
        ts2.addX(41.39);
        ts2.addX(41.60);
        //System.out.println(HoltsLinearTrendMethod.forecast(ts2,0.8,0.2,1));
        System.out.println(HoltsLinearTrendMethod.fit(ts2, 0.8, 0.2, 0));
        //System.out.println(ExponentialTrendMethod.fit(ts2, 0.8, 0.2, 5));
        //System.out.println(DampedTrendMethod.fit(ts2,0.8,0.2,0.85,5));
        //System.out.println(MultiplicativeDampedTrend.fit(ts2,0.8,0.2,0.98,5));
        System.out.println(Evaluator.RootMeanSquaredError(ts2, HoltsLinearTrendMethod.fit(ts2, 0.98, 0,0)));
    }
}
