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
import timeseries.TimeSeries;

/**
 * Created by apolol92 on 21.08.15.
 * Simple exponential smoothing(SES) uses a weighted moving average with weights that decrease exponentially as
 * observations come from further in the past.
 * SES only has a flat forecast function, for longer forecast horizons h >= 2, we get: y(t+h|T) = y(t+1|T).
 * Disadvantage of SES: It is only suitable for forecasting data
 * with no trend and no seasonal pattern.
 */
public class SimpleExponentialSmoothing {
    /*
        The smoothing parameter alpha with 0 < alpha <= 1 controls the weights decrease.
     */
    public static RegularlyTimeSeries _fit(RegularlyTimeSeries ts, double alpha, long h) {
        RegularlyTimeSeries rts = new RegularlyTimeSeries(ts.getSpacing());
        double lt_1 = ts.getX(0);
        boolean first = true;
        double lt = 0;
        for(long i = 0; i < ts.size(); i++) {
            lt = alpha*ts.getX(i)+(1-alpha)*lt_1;
            if(first) {
                rts.addFirstDataPoint(ts.getT(0),lt);
                first = false;
            }
            else {
                rts.addX(lt);
            }
            lt_1 = lt;
        }
        for(long i = 0; i < h; i++) {
            rts.addX(lt);
        }
        return rts;
    }







}
