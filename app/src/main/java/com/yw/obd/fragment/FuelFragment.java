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
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

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
    @Bind(R.id.pineChart)
    PieChartView pineChart;

    private ColumnChartData columnChartData;
    private LineChartData lineChartData;
    private PieChartData pieChartData;

    private int numberOfLines = 1;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 7;
    private float[][] randomNumberTab = new float[maxNumberOfLines][numberOfPoints];
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor = false;

    private DeviceListInfo deviceListInfo;
    private List<Float> data;
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

        data = new ArrayList<>();
        for (int i = 0; i < numberOfPoints; i++) {
            float value = (float) Math.random() * 50f;
            data.add(value);
        }
        getDeviceList();
//        initColumnChart();
//        initLineChart();
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

    private List<Float> dayOils = new ArrayList<>();

    private void getOil(String deviceId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Http.getCarOil(getActivity(), sdf.format(new Date(System.currentTimeMillis())), deviceId, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                String res = (String) object;
                try {
                    int state = Integer.parseInt(new JSONObject(res).getString("state"));
                    switch (state) {
                        case 0:
                            oilInfo = new Gson().fromJson(res, OilInfo.class);
                            String oiLday1 = oilInfo.getOILday1();
                            String oiLday2 = oilInfo.getOILday2();
                            String oiLday3 = oilInfo.getOILday3();
                            String oiLday4 = oilInfo.getOILday4();
                            String oiLday5 = oilInfo.getOILday5();
                            String oiLday6 = oilInfo.getOILday6();
                            String oiLday7 = oilInfo.getOILday7();
                            dayOils.clear();
                            dayOils.add(Float.parseFloat(oiLday1));
                            dayOils.add(Float.parseFloat(oiLday2));
                            dayOils.add(Float.parseFloat(oiLday3));
                            dayOils.add(Float.parseFloat(oiLday4));
                            dayOils.add(Float.parseFloat(oiLday5));
                            dayOils.add(Float.parseFloat(oiLday6));
                            dayOils.add(Float.parseFloat(oiLday7));
                            initColumnChart(dayOils);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initColumnChart(List<Float> data) {
        List<Integer> xValues = new ArrayList<>();//横坐标值
        Log.d("print", "data" + data);
        //柱状图相关
        List<Column> columnList = new ArrayList<>();
        List<SubcolumnValue> subcolumnValues;
        List<AxisValue> axisValues = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            subcolumnValues = new ArrayList<>();
            subcolumnValues.add(new SubcolumnValue(data.get(i), ChartUtils.COLOR_BLUE));
            xValues.add(data.size() - i);

            Column column = new Column(subcolumnValues);
            columnList.add(column);
            axisValues.add(new AxisValue(i).setLabel(xValues.get(i) + ""));
        }
        columnChartData = new ColumnChartData(columnList);

         /*===== 坐标轴相关设置 =====*/
        Axis axisX = new Axis(axisValues);
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("日期");    //设置横轴名称
        axisY.setName("油耗");    //设置竖轴名称
        columnChartData.setAxisXBottom(axisX); //设置横轴
        columnChartData.setAxisYLeft(axisY);   //设置竖轴

        //以上所有设置的数据、坐标配置都已存放到mColumnChartData中，接下来给mColumnChartView设置这些配置
        columnChart.setColumnChartData(columnChartData);
    }


    private void initLineChart() {
        //折线图相关
        generateValues();
        generateData();
    }

    private void generateValues() {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumberTab[i][j] = data.get(j);
            }
        }
    }

    private void generateData() {

        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<>();
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, randomNumberTab[i][j]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            if (pointsHaveDifferentColor) {
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        lineChartData = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("日期");
                axisY.setName("油耗");
            }
            lineChartData.setAxisXBottom(axisX);
            lineChartData.setAxisYLeft(axisY);
        } else {
            lineChartData.setAxisXBottom(null);
            lineChartData.setAxisYLeft(null);
        }

        lineChartData.setBaseValue(Float.NEGATIVE_INFINITY);
        lineChart.setLineChartData(lineChartData);
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
