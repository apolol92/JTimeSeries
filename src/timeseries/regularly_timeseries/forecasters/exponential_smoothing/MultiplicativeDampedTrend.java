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
 * Created by apolol92 on 22.08.15.
 */
public class MultiplicativeDampedTrend {

    public static RegularlyTimeSeries fit(RegularlyTimeSeries ts, double alpha, double beta, double damped, long h) {
        RegularlyTimeSeries rts = new RegularlyTimeSeries(ts.getSpacing());
        long size = ts.size();
        double lt_1 = ts.getX(0);
        System.out.println(lt_1);
        double bt_1 = ts.getX(1) / ts.getX(0);
        System.out.println(bt_1);
        double lt = 0;
        double bt = 0;
        //System.out.println(bt_1);
        rts.addFirstDataPoint(ts.getT(0), lt_1 * Math.pow(bt_1, 1));
        for (long i = 0; i < ts.size(); i++) {
            lt = alpha * ts.getX(i) + (1 - alpha) * (lt_1 * bt_1);
            System.out.println(lt);
            bt = beta * (lt / lt_1) + (1 - beta) * bt_1;
            if (i < ts.size() - 1) {
                rts.addX(lt * Math.pow(bt, damped));
                lt_1 = lt;
                bt_1 = bt;
            }

        }
        //Not sure..
        for (double i = 1; i <= h; i++) {
            double df = 0;
            for(int d = 1; d <= i; d++) {
                df+=Math.pow(damped,d);
            }
            rts.addX(lt * Math.pow(bt, df));
        }
        return rts;
    }
}
