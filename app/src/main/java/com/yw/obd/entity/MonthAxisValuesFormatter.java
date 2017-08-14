package com.yw.obd.entity;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * Created by apollo on 2017/8/14.
 */

public class MonthAxisValuesFormatter implements IAxisValueFormatter {

    protected String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };


    private List<String> data;

    public MonthAxisValuesFormatter(List<String> data){
        this.data=data;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return null;
    }
}
