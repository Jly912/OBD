package com.yw.obd.fragment;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.base.BaseFragment;
import com.yw.obd.entity.DeviceListInfo;
import com.yw.obd.entity.OilInfo;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by apollo on 2017/7/25.
 */

public class FuelFragment extends BaseFragment {
    @Bind(R.id.sp)
    Spinner sp;
    @Bind(R.id.iv_drop)
    ImageButton ivDrop;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_rank)
    TextView tvRank;
    @Bind(R.id.tv_detail)
    TextView tvDetail;
    @Bind(R.id.column_chart)
    ColumnChartView columnChart;
    @Bind(R.id.lineChart)
    LineChartView lineChart;
    @Bind(R.id.hBarChart)
    HorizontalBarChart hBarChart;

    private ColumnChartData columnChartData;
    private LineChartData lineChartData;

    private int numberOfLines = 1;
    private int maxNumberOfLines = 5;
    private int numberOfPoints = 5;
    private float[][] randomNumberTab = new float[maxNumberOfLines][numberOfPoints];
    private boolean hasAxes = true;

    private DeviceListInfo deviceListInfo;
    private List<Map<String, Object>> spData;
    private String selectDeviceName = "";
    private AlertDialog loadingDia;
    private OilInfo oilInfo;


    public static FuelFragment getInstance() {
        FuelFragment fuelFragment = new FuelFragment();
        return fuelFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fuel;
    }

    @Override
    protected void initView(View view) {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.progressdialog, null);
        loadingDia = new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setView(inflate)
                .create();
        loadingDia.show();

        getDeviceList();
    }

    private int selectId = 0;
    private String deviceId = "";

    private void initSpanner(final DeviceListInfo deviceListInfo, int selectDevice) {
        sp.setPrompt(getResources().getString(R.string.select_car));
        spData = new ArrayList<>();
        for (int i = 0; i < deviceListInfo.getArr().size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", deviceListInfo.getArr().get(i).getName());
            map.put("number", deviceListInfo.getArr().get(i).getCarNum());
            String deviceId1 = deviceListInfo.getArr().get(i).getId();
            if (Integer.parseInt(deviceId1) == selectDevice) {
                deviceId = deviceId1;
                selectId = i;
            }

            spData.add(map);
        }

        Log.e("print", "Fuel----" + AppData.GetInstance(getActivity()).getSelectedDevice() + "______" + deviceId);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), spData, R.layout.layout_spinner, new String[]{"name", "number"},
                new int[]{R.id.tv_car, R.id.tv_car_number});
        sp.setAdapter(adapter);
        sp.setSelection(selectId);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("print", "position" + position);
                String device_id = deviceListInfo.getArr().get(position).getId();
                selectDeviceName = deviceListInfo.getArr().get(position).getName();
                AppData.GetInstance(getActivity()).setSelectedDevice(Integer.parseInt(device_id));
                getOil(deviceListInfo.getArr().get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getDeviceList() {
        if (loadingDia != null && loadingDia.isShowing()) {
            loadingDia.dismiss();
        }
        Http.getDeviceList(getActivity(), new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                String res = (String) object;
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    int state = Integer.parseInt(jsonObject.getString("state"));
                    switch (state) {
                        case 0:
                            deviceListInfo = new Gson().fromJson(res, DeviceListInfo.class);
                            initSpanner(deviceListInfo, AppData.GetInstance(getActivity()).getSelectedDevice());
                            break;
                        case 2002:
                            AppData.showToast(getActivity(), R.string.no_msg);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private List<Float> dayOils = new ArrayList<>();//每日油耗数据源
    private List<Float> weekOils = new ArrayList<>();//每周油耗数据源
    private List<Float> monthOils = new ArrayList<>();//每月油耗数据源
    private List<Integer> dates = new ArrayList<>();//用于存放柱状图横坐标
    private List<String> weeks = new ArrayList<>();//用于存放折线图横坐标
    private List<String> months = new ArrayList<>();

    private void getOil(String deviceId) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Http.getCarOil(getActivity(), sdf.format(new Date(System.currentTimeMillis())), deviceId, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                String res = (String) object;
                try {
                    int state = Integer.parseInt(new JSONObject(res).getString("state"));
                    switch (state) {
                        case 0:
                            oilInfo = new Gson().fromJson(res, OilInfo.class);
                            //日数据
                            String oiLday1 = oilInfo.getOILday1();
                            String oiLday2 = oilInfo.getOILday2();
                            String oiLday3 = oilInfo.getOILday3();
                            String oiLday4 = oilInfo.getOILday4();
                            String oiLday5 = oilInfo.getOILday5();
                            String oiLday6 = oilInfo.getOILday6();
                            String oiLday7 = oilInfo.getOILday7();
                            //日数据初始化
                            dayOils.clear();
                            dayOils.add(Float.parseFloat(oiLday1));
                            dayOils.add(Float.parseFloat(oiLday2));
                            dayOils.add(Float.parseFloat(oiLday3));
                            dayOils.add(Float.parseFloat(oiLday4));
                            dayOils.add(Float.parseFloat(oiLday5));
                            dayOils.add(Float.parseFloat(oiLday6));
                            dayOils.add(Float.parseFloat(oiLday7));

                            //横坐标日期初始化
                            dates.clear();
                            for (int i = 0; i < 7; i++) {
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(new Date());
                                cal.add(Calendar.DATE, -i);
                                String date = sdf.format(cal.getTime());
                                String[] split = date.split("-");
                                String day = split[2];
                                int da = Integer.parseInt(day);
                                dates.add(da);
                            }

                            initColumnChart(dayOils, dates);

                            //周数据
                            String oiLweek1 = oilInfo.getOILweek1();
                            String oiLweek2 = oilInfo.getOILweek2();
                            String oiLweek3 = oilInfo.getOILweek3();
                            String oiLweek4 = oilInfo.getOILweek4();
                            weekOils.clear();
                            weekOils.add(0f);
                            weekOils.add(Float.parseFloat(oiLweek1));
                            weekOils.add(Float.parseFloat(oiLweek2));
                            weekOils.add(Float.parseFloat(oiLweek3));
                            weekOils.add(Float.parseFloat(oiLweek4));

                            initLineChart(weekOils);

                            //月数据
                            String oiLmonth1 = oilInfo.getOILmonth1();
                            String oiLmonth2 = oilInfo.getOILmonth2();
                            String oiLmonth3 = oilInfo.getOILmonth3();
                            monthOils.clear();
                            monthOils.add(Float.parseFloat(oiLmonth1));
                            monthOils.add(Float.parseFloat(oiLmonth2));
                            monthOils.add(Float.parseFloat(oiLmonth3));

                            monthOils.add(10.5f);
                            monthOils.add(2.5f);
                            monthOils.add(4.5f);
                            monthOils.add(7.5f);

                            //横坐标日期初始化
                            months.clear();
                            for (int i = 0; i < 3; i++) {
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(new Date());
                                cal.add(Calendar.MONTH, -i);
                                String date = sdf.format(cal.getTime());
                                String[] split = date.split("-");
                                String month = split[1];
                                int mo = Integer.parseInt(month);
                                months.add(mo + getResources().getString(R.string.month));
                            }

                            initHBBar(monthOils, months);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initColumnChart(List<Float> data, List<Integer> xValues) {
        //柱状图相关
        List<Column> columnList = new ArrayList<>();
        List<SubcolumnValue> subcolumnValues;
        List<AxisValue> axisValues = new ArrayList<>();//永远存放横坐标值

        for (int i = 0; i < data.size(); i++) {
            subcolumnValues = new ArrayList<>();
            subcolumnValues.add(new SubcolumnValue(data.get(i), ChartUtils.COLOR_BLUE));

            Column column = new Column(subcolumnValues);
            column.setHasLabels(true);// 柱状图是否就展示数据量
            columnList.add(column);
            axisValues.add(new AxisValue(i).setLabel(xValues.get(i) + ""));
        }
        columnChartData = new ColumnChartData(columnList);

         /*===== 坐标轴相关设置 =====*/
        Axis axisX = new Axis(axisValues);
        axisX.setTextColor(getResources().getColor(R.color.tab_bg));//设置X轴文字颜色
        Axis axisY = new Axis().setHasLines(true);
        axisY.setTextColor(getResources().getColor(R.color.tab_bg));//设置Y轴文字颜色
        columnChartData.setAxisXBottom(axisX); //设置横轴
        columnChartData.setAxisYLeft(axisY);   //设置竖轴

        //以上所有设置的数据、坐标配置都已存放到mColumnChartData中，接下来给mColumnChartView设置这些配置
        columnChart.setColumnChartData(columnChartData);
    }

    private void initLineChart(List<Float> data) {
        //折线图相关
        generateValues(data);
        generateData(data);
    }

    private void generateValues(List<Float> data) {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumberTab[i][j] = data.get(j);
            }
        }
    }

    private void generateData(List<Float> data) {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < numberOfLines; ++i) {
            List<PointValue> values = new ArrayList<>();
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, data.get(j)));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_ORANGE);//设置折线图颜色
            line.setShape(ValueShape.CIRCLE);//节点图形样式 DIAMOND菱形、SQUARE方形、CIRCLE圆形
            line.setCubic(false);//节点之间的过渡
            line.setFilled(false);//是否设置折线覆盖区域的颜色
            line.setHasLabels(true);//是否显示节点数据
            line.setHasLabelsOnlyForSelected(false);//隐藏数据，触摸可以显示
            line.setHasLines(true);//是否显示折线
            line.setHasPoints(true);//是否显示这点
            line.setPointColor(ChartUtils.COLOR_ORANGE);//设置节点颜色

            lines.add(line);//将数据集合添加到线
        }

        lineChartData = new LineChartData(lines);

        weeks.clear();

        weeks.add("");
        weeks.add(getResources().getString(R.string.this_week));
        weeks.add(getResources().getString(R.string.last_week));
        weeks.add(getResources().getString(R.string.l_last_week));
        weeks.add(getResources().getString(R.string.ll_last_week));


        List<AxisValue> xValues = new ArrayList<>();
        for (int i = 0; i < weeks.size(); i++) {
            xValues.add(new AxisValue(i).setLabel(weeks.get(i)));
        }

        if (hasAxes) {
            Axis axisX = new Axis(xValues);
            axisX.setTextColor(getResources().getColor(R.color.tab_bg));//设置X轴文字颜色
            Axis axisY = new Axis().setHasLines(true);
            axisY.setTextColor(getResources().getColor(R.color.tab_bg));//设置Y轴文字颜色
            lineChartData.setAxisXBottom(axisX);
            lineChartData.setAxisYLeft(axisY);//将Y轴属性设置到左边
        } else {
            lineChartData.setAxisXBottom(null);
            lineChartData.setAxisYLeft(null);
        }
        lineChartData.setBaseValue(Float.NEGATIVE_INFINITY);//设置反向覆盖区域颜色
        lineChart.setLineChartData(lineChartData);//将数据添加到控件中
    }

    private void initHBBar(List<Float> datas, List<String> xValues) {
//        hBarChart.setOnChartValueSelectedListener(this);
        // mChart.setHighlightEnabled(false);

        hBarChart.setDrawBarShadow(false);
        hBarChart.setDrawValueAboveBar(true);
        hBarChart.getDescription().setEnabled(false);
        // drawn
        hBarChart.setMaxVisibleValueCount(60);
        hBarChart.setPinchZoom(false);
        hBarChart.setDrawGridBackground(false);
        hBarChart.setNoDataText(getResources().getString(R.string.no_data));

        IndexAxisValueFormatter in=new IndexAxisValueFormatter(xValues);

        XAxis xl = hBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);

        YAxis yl = hBarChart.getAxisLeft();
        yl.setValueFormatter(in);
        yl.setTextSize(10f);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = hBarChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

        float barWidth = 5f;
        float spaceForBar = 10f;
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < datas.size(); i++) {
            float val = datas.get(i);
            yVals1.add(new BarEntry(i * spaceForBar, val));
        }

        BarDataSet da = new BarDataSet(yVals1, "");
        da.setColor(getResources().getColor(R.color.green));//设置水平柱状图的颜色
        da.setDrawIcons(false);//是否绘制icon
        da.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return null;
            }
        });
        ArrayList<IBarDataSet> datasets = new ArrayList<>();
        datasets.add(da);

        BarData ba = new BarData(datasets);
        ba.setBarWidth(barWidth);
        ba.setValueTextSize(10f);
        hBarChart.setData(ba);
        hBarChart.invalidate();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            getDeviceList();
        }
    }

    @OnClick({R.id.iv_drop, R.id.tv_rank, R.id.tv_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_drop:
                break;
            case R.id.tv_rank:
                break;
            case R.id.tv_detail:
                break;
        }
    }
}
