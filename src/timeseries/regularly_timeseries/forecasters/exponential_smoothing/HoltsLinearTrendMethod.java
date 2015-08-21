/*
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 */

package timeseries.regularly_timeseries.forecasters.exponential_smoothing;

import timeseries.RegularlyTimeSeries;

/**
 * Created by apolol92 on 21.08.15.
 * Holt extended simple exponential smoothing to allow forecasting of data with a trend.
 * This method involves a forecast equation and two smoothing equations(Level Equation and Trend Equation).
 */
public class HoltsLinearTrendMethod {



    public static RegularlyTimeSeries fit(RegularlyTimeSeries ts, double alpha, double beta, double h) {
        RegularlyTimeSeries rts = new RegularlyTimeSeries(ts.getSpacing());
        long size = ts.size();
        double lt_1 = ts.getX(0);
        double bt_1 = ts.getGradient(0);
        double lt = 0;
        double bt = 0;
        //System.out.println(bt_1);
        rts.addFirstDataPoint(ts.getT(0),lt_1+1*bt_1);
        for(long i = 0; i < ts.size(); i++) {
            lt = alpha * ts.getX(i) + (1 - alpha) * (lt_1 + bt_1);
            bt = beta * (lt - lt_1) + (1 - beta) * bt_1;
            if(i<ts.size()-1) {
                rts.addX(lt + 1 * bt);
                lt_1 = lt;
                bt_1 = bt;
            }

        }
        for(double i = 1; i <= h; i++) {
            rts.addX(lt+i*bt);
        }
        return rts;
    }
}
