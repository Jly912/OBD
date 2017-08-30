package com.yw.obd.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.activity.OilDetailActivity;
import com.yw.obd.adapter.PopuLvAdapter;
import com.yw.obd.base.BaseFragment;
import com.yw.obd.entity.DeviceListInfo;
import com.yw.obd.entity.OilInfo;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;
import com.yw.obd.util.DensityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by apollo on 2017/7/25.
 */

public class FuelFragment extends BaseFragment {

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

    @Bind(R.id.ll_car)
    LinearLayout llCar;
    @Bind(R.id.rl)
    RelativeLayout rl;
    @Bind(R.id.tv_car)
    TextView tvCarName;
    @Bind(R.id.tv_car_number)
    TextView tvCarNum;
    private ColumnChartData columnChartData;
    private LineChartData lineChartData;

    private int numberOfLines = 1;
    private int maxNumberOfLines = 5;
    private int numberOfPoints = 5;
    private float[][] randomNumberTab = new float[maxNumberOfLines][numberOfPoints];
    private boolean hasAxes = true;

    private DeviceListInfo deviceListInfo;
    private AlertDialog loadingDia;
    private OilInfo oilInfo;

    private String device_id, carName;
    private PopupWindow popupWindow;
    private View popu;
    private ListView lv;
    private LinearLayout ll;
    private PopuLvAdapter adapter;

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

        popu = getActivity().getLayoutInflater().inflate(R.layout.layout_popu, null);
        lv = (ListView) popu.findViewById(R.id.lv);
        ll = (LinearLayout) popu.findViewById(R.id.ll_out);
        ll.setBackgroundColor(getResources().getColor(R.color.white));

        firstInit();
        getDeviceList();
    }


    private void firstInit() {
        List<Float> data = new ArrayList<>();
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
            data.add(0.0f);
        }
        initColumnChart(data, dates);

        List<Float> dd = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            dd.add(0.0f);
        }
        initLineChart(dd, 0);

        List<Float> mm = new ArrayList<>();

        //横坐标日期初始化
        months.clear();
        months.add("");
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

        months.add("");
        initHBBar(dd, months);
    }

    private void showPopu(View parent) {
        if (popupWindow == null) {
            adapter = new PopuLvAdapter(getActivity(), deviceListInfo, true);
            lv.setAdapter(adapter);
            int width = DensityUtil.dip2px(getActivity(), 150);
            int height = DensityUtil.dip2px(getActivity(), 150);
            popupWindow = new PopupWindow(popu, width, height);
        }

        adapter.notifyDataSetChanged();
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        int xPos = windowManager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;
        Log.i("print", "xPos:" + xPos);

        popupWindow.showAsDropDown(parent, xPos, 0);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {

                if (popupWindow != null) {
                    popupWindow.dismiss();
                }

                device_id = deviceListInfo.getArr().get(position).getId();
                carName = deviceListInfo.getArr().get(position).getName();
                tvCarName.setText(carName);
                tvCarNum.setText(deviceListInfo.getArr().get(position).getCarNum());
                AppData.GetInstance(getActivity()).setSelectedDevice(Integer.parseInt(device_id));
                AppData.GetInstance(getActivity()).setSN(deviceListInfo.getArr().get(position).getSn());
                getOil(deviceListInfo.getArr().get(position).getId());

            }
        });
    }

    private boolean isExist = false;

    private void getDeviceList() {
//        loadingDia.show();
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

                            int selectedDevice = AppData.GetInstance(getActivity()).getSelectedDevice();
                            for (int i = 0; i < deviceListInfo.getArr().size(); i++) {
                                if (deviceListInfo.getArr().get(i).getId().equals(selectedDevice + "")) {
                                    device_id = deviceListInfo.getArr().get(i).getId();
                                    carName = deviceListInfo.getArr().get(i).getName();
                                    tvCarName.setText(carName);
                                    tvCarNum.setText(deviceListInfo.getArr().get(i).getCarNum());
                                    AppData.GetInstance(getActivity()).setSN(deviceListInfo.getArr().get(i).getSn());
                                    getOil(deviceListInfo.getArr().get(i).getId());
                                    isExist = true;
                                    return;
                                }
                            }

                            if (!isExist) {
                                device_id = deviceListInfo.getArr().get(0).getId();
                                carName = deviceListInfo.getArr().get(0).getName();
                                tvCarName.setText(carName);
                                tvCarNum.setText(deviceListInfo.getArr().get(0).getCarNum());
                                AppData.GetInstance(getActivity()).setSelectedDevice(Integer.parseInt(device_id));
                                AppData.GetInstance(getActivity()).setSN(deviceListInfo.getArr().get(0).getSn());
                                getOil(deviceListInfo.getArr().get(0).getId());
                            }

                            break;
                        case 2002:
                            if (loadingDia != null && loadingDia.isShowing()) {
                                loadingDia.dismiss();
                            }
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
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private void getOil(String deviceId) {
//        loadingDia.show();
        Http.getCarOil(getActivity(), sdf.format(new Date(System.currentTimeMillis())), deviceId, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (loadingDia != null && loadingDia.isShowing()) {
                    loadingDia.dismiss();
                }
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

                            List<Integer> dat = new ArrayList<Integer>();
                            for (int i = 0; i < weekOils.size(); i++) {
                                dat.add(weekOils.get(i).intValue());
                            }

                            Log.e("print", "dat---" + dat + "===max" + Collections.max(dat));
                            initLineChart(weekOils, (Collections.max(dat) / 10 + 1) * 10);

                            //月数据
                            String oiLmonth1 = oilInfo.getOILmonth1();
                            String oiLmonth2 = oilInfo.getOILmonth2();
                            String oiLmonth3 = oilInfo.getOILmonth3();
                            monthOils.clear();

                            monthOils.add(0.0f);
                            monthOils.add(Float.parseFloat(oiLmonth1));
                            monthOils.add(Float.parseFloat(oiLmonth2));
                            monthOils.add(Float.parseFloat(oiLmonth3));
                            monthOils.add(0.0f);

                            //横坐标日期初始化
                            months.clear();
                            months.add("");
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

                            months.add("");

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
        List<AxisValue> axisValues = new ArrayList<>();//用于存放横坐标值

        for (int i = 0; i < data.size(); i++) {
            subcolumnValues = new ArrayList<>();
            subcolumnValues.add(new SubcolumnValue(data.get(i), ChartUtils.COLOR_BLUE));
            Column column = new Column(subcolumnValues);
            column.setHasLabels(false);// 柱状图是否就展示数据量
            column.setHasLabelsOnlyForSelected(false);
            columnList.add(column);
            axisValues.add(new AxisValue(i).setLabel(xValues.get(i) + ""));
        }

        columnChartData = new ColumnChartData(columnList);
        columnChartData.setFillRatio(0.3f);//设置柱子的宽度

         /*===== 坐标轴相关设置 =====*/
        Axis axisX = new Axis(axisValues);
        axisX.setTextColor(getResources().getColor(R.color.tab_bg));//设置X轴文字颜色
        axisX.setHasLines(true);
        Axis axisY = new Axis();
        axisY.setTextColor(getResources().getColor(R.color.tab_bg));//设置Y轴文字颜色
        axisY.setHasLines(true);
        columnChartData.setAxisXBottom(axisX); //设置横轴
        columnChartData.setAxisYLeft(axisY);   //设置竖轴

        //以上所有设置的数据、坐标配置都已存放到mColumnChartData中，接下来给mColumnChartView设置这些配置
        columnChart.setColumnChartData(columnChartData);
    }

    private void initLineChart(List<Float> data, int top) {
        //折线图相关
        generateValues(data);
        generateData(data);
        lineChart.setViewportCalculationEnabled(false);
        resetViewport(top);
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

            Log.e("print", "values---" + values);
            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_ORANGE);//设置折线图颜色
            line.setShape(ValueShape.CIRCLE);//节点图形样式 DIAMOND菱形、SQUARE方形、CIRCLE圆形
            line.setCubic(false);//节点之间的过渡
            line.setFilled(false);//是否设置折线覆盖区域的颜色
            line.setHasLabels(false);//是否显示节点数据
            line.setHasLabelsOnlyForSelected(false);//隐藏数据，触摸可以显示
            line.setHasLines(true);//是否显示折线
            line.setHasPoints(true);//是否显示这点
            line.setPointColor(ChartUtils.COLOR_ORANGE);//设置节点颜色
            lines.add(line);//将数据集合添加到线
        }

        lineChartData = new LineChartData(lines);
        lineChartData.setBaseValue(20);


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
            axisX.setHasLines(true);
            axisX.setTextColor(getResources().getColor(R.color.tab_bg));//设置X轴文字颜色
            Axis axisY = new Axis().setHasLines(true);
            axisY.setMaxLabelChars(6);//max label length, for example 60
            List<AxisValue> values = new ArrayList<>();
            for (int i = 0; i < 100; i += 10) {
                AxisValue value = new AxisValue(i);
                String label = i + "";
                value.setLabel(label);
                values.add(value);
            }
            axisY.setValues(values);
            axisY.setTextColor(getResources().getColor(R.color.tab_bg));//设置Y轴文字颜色
            lineChartData.setAxisXBottom(axisX);
            lineChartData.setAxisYLeft(axisY);//将Y轴属性设置到左边
            lineChartData.getAxisYLeft().setHasLines(true);
        } else {
            lineChartData.setAxisXBottom(null);
            lineChartData.setAxisYLeft(null);
        }
        lineChartData.setBaseValue(Float.NEGATIVE_INFINITY);//设置反向覆盖区域颜色
        lineChart.setLineChartData(lineChartData);//将数据添加到控件中
    }

    private void resetViewport(int top) {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.bottom = 0;
        if (top == 0) {
            top = 100;
        }
        v.top = top;
        v.left = 0;
        v.right = numberOfPoints;
        lineChart.setMaximumViewport(v);
        lineChart.setCurrentViewport(v);
    }

    private void initHBBar(final List<Float> datas, final List<String> xValues) {
        hBarChart.setDrawBarShadow(false);//是否绘制柱状图边框
        hBarChart.setDrawValueAboveBar(true);//是否将数值显示在柱状图以外，false是显示在柱状图上
        hBarChart.getDescription().setEnabled(false);
        // drawn
        hBarChart.setMaxVisibleValueCount(60);//设置Y轴左边的最大值
        hBarChart.setPinchZoom(true);//是否横纵一起缩放
        hBarChart.setDrawGridBackground(false);//是否画网格
        hBarChart.setNoDataText(getResources().getString(R.string.no_data));//设置图表没有数据时显示的数据

        XAxis xl = hBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value == 0.f) {
                    return xValues.get(0);
                } else if (value == 10.f) {
                    return xValues.get(1);
                } else if (value == 20.f) {
                    return xValues.get(2);
                } else if (value == 30.f) {
                    return xValues.get(3);
                }
                return xValues.get(4);
            }

        });
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);

        YAxis yl = hBarChart.getAxisLeft();
        yl.setTextSize(10f);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);//Y轴数据是否反转在右侧，同时x轴数据反转

        YAxis yr = hBarChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = hBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);

        float barWidth = 5f;
        float spaceForBar = 10f;
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < datas.size(); i++) {
            float val = datas.get(i);
            BarEntry barEntry = new BarEntry(i * spaceForBar, val);
            yVals1.add(barEntry);
        }

        BarDataSet da = new BarDataSet(yVals1, getResources().getString(R.string.coefficient));
        da.setColor(getResources().getColor(R.color.green));//设置水平柱状图的颜色
        da.setDrawIcons(false);//是否绘制icon
        ArrayList<IBarDataSet> datasets = new ArrayList<>();
        datasets.add(da);

        BarData ba = new BarData(datasets);
        ba.setBarWidth(barWidth);//宽度
        ba.setValueTextSize(10f);//字体大小
        ba.setDrawValues(true);//图表是否显示数值
        hBarChart.setData(ba);
        hBarChart.setFitBars(true);//如果设置为true，图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        hBarChart.invalidate();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            getDeviceList();
        } else {
            if (loadingDia != null) {
                loadingDia.dismiss();
            }
        }
    }

    @OnClick({R.id.tv_rank, R.id.tv_detail, R.id.ll_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_rank:
                break;
            case R.id.tv_detail:
                if (deviceListInfo == null) {
                    AppData.showToast(getActivity(), R.string.add_device_first);
                    return;
                }
                Intent intent = new Intent(getActivity(), OilDetailActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.ll_car:
                if (deviceListInfo == null) {
                    AppData.showToast(getActivity(), R.string.add_device_first);
                    return;
                }
                showPopu(rl);
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (loadingDia != null) {
            loadingDia.dismiss();
            loadingDia = null;
        }
        super.onDestroy();
    }
}
