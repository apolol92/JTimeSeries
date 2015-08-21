/*
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 */

package timeseries.regularly_timeseries.forecasters;

import timeseries.RegularlyTimeSeries;

/**
 * Created by apolol92 on 21.08.15.
 * This class offers static methods for simple forecasting..
 */
public class SimpleForecaster {

    /**
     * mean value of historical data
     * @param ts, historical data
     * @return forecast
     */
    public static double averageMethod(RegularlyTimeSeries ts) {
        return ts.mean();
    }

    /**
     * last observed value
     * @param ts, historical data
     * @return forecast
     */
    public static double naive_method(RegularlyTimeSeries ts) {
        return ts.getX(ts.size());
    }

}
