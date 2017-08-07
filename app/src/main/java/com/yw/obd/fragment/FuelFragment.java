package com.yw.obd.fragment;

import android.view.View;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.base.BaseFragment;

import java.util.ArrayList;
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
    private int numberOfPoints = 31;
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

    private  List<Float> data ;
    private List<Map<String,Object>> spData;
    private String[] names = { "大众·CC", "大众·CA", "大众·BB", "大众·CB", "大众·DD", "大众·EE", "大众·FF", "大众·GG" };

    private String[] numbers = { "粤B·6666",  "粤B·6677",  "粤B·7788",  "粤B·8899",  "粤B·8800",  "粤B·9900",  "粤B·1122",  "粤B·1212" };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fuel;
    }

    @Override
    protected void initView(View view) {
        data = new ArrayList<>();
        for (int i = 0; i < numberOfPoints; i++) {
            float value = (float) Math.random() * 50f;
            data.add(value);
        }
        initSpanner();
        initColumnChart();
        initLineChart();
    }

    private void initSpanner(){
        sp.setPrompt("选择车辆");
        spData=new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Map<String,Object> map=new HashMap<>();
            map.put("name",names[i]);
            map.put("number",numbers[i]);
            spData.add(map);
        }

        SimpleAdapter adapter=new SimpleAdapter(getActivity(),spData,R.layout.layout_spinner,new String[]{"name","number"},
                new int[]{R.id.tv_car,R.id.tv_car_number});
        sp.setAdapter(adapter);
    }

    private void initColumnChart() {
        List<Integer> xValues = new ArrayList<>();//横坐标值

        //柱状图相关
        List<Column> columnList = new ArrayList<>();
        List<SubcolumnValue> subcolumnValues;
        List<AxisValue> axisValues = new ArrayList<>();

        for (int i = 0; i < numberOfPoints; i++) {
            subcolumnValues = new ArrayList<>();
            subcolumnValues.add(new SubcolumnValue(data.get(i), ChartUtils.pickColor()));
            xValues.add(31 - i);

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
