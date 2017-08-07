package com.yw.obd.activity;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.app.AppContext;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.util.WeightUtil;
import com.yw.obd.widget.FhistogramView;
import com.yw.obd.widget.HistogramView;

import butterknife.Bind;

/**
 * Created by apollo on 2017/8/2.
 */

public class SampleActivity extends BaseActivity {
    @Bind(R.id.histogramView)
    FhistogramView histogramView;
    @Bind(R.id.btn_hv1)
    Button btnHv1;
    @Bind(R.id.hv1)
    HistogramView hv1;
    @Bind(R.id.btn_hv2)
    Button btnHv2;
    @Bind(R.id.hv2)
    HistogramView hv2;
    @Bind(R.id.tv_hv2)
    TextView tvHv2;
    @Bind(R.id.btn_hv3)
    Button btnHv3;
    @Bind(R.id.hv3)
    HistogramView hv3;
    @Bind(R.id.btn_hv4)
    Button btnHv4;
    @Bind(R.id.hv4)
    HistogramView hv4;
    @Bind(R.id.tv_hv4)
    TextView tvHv4;
    @Bind(R.id.btn_hv5)
    Button btnHv5;
    @Bind(R.id.hv5)
    HistogramView hv5;
    @Bind(R.id.btn_hv6)
    Button btnHv6;
    @Bind(R.id.hv6)
    HistogramView hv6;
    private Context context=AppContext.getContext();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sample;
    }

    @Override
    protected void init() {
        new WeightUtil(context).mLayoutParams_Btn(btnHv1);

        hv1.setProgress(0.2);
        hv1.setRateBackgroundColor("#FF8B6F");
        hv1.setOrientation(LinearLayout.HORIZONTAL);
        new WeightUtil(context).mLayoutParams_View(hv1);

        new WeightUtil(context).mLayoutParams_Btn(btnHv2);
        double d=293;
        double b=700;
        double p2=d/b;

        tvHv2.setText("￥293.00");
        new WeightUtil(context).mLayoutParams_Text(tvHv2,p2);


        hv2.setProgress(p2);
        new WeightUtil(context).mLayoutParams_View(hv2);
        hv2.setRateBackgroundColor("#F8D478");
        hv2.setOrientation(LinearLayout.HORIZONTAL);

        new WeightUtil(context).mLayoutParams_Btn(btnHv3);

        hv3.setProgress(0.6);
        new WeightUtil(context).mLayoutParams_View(hv3);
        hv3.setRateBackgroundColor("#72CFF6");
        hv3.setOrientation(LinearLayout.HORIZONTAL);

        double d4=553;
        double p4=d4/b;
        new WeightUtil(context).mLayoutParams_Btn(btnHv4);

        tvHv4.setText("￥553.00");
        new WeightUtil(context).mLayoutParams_Text(tvHv4,p4);

        hv4.setProgress(p4);
        new WeightUtil(context).mLayoutParams_View(hv4);
        hv4.setRateBackgroundColor("#9F88C7");
        hv4.setOrientation(LinearLayout.HORIZONTAL);

        new WeightUtil(context).mLayoutParams_Btn(btnHv5);

        hv5.setProgress(1);
        new WeightUtil(context).mLayoutParams_View(hv5);
        hv5.setRateBackgroundColor("#55D99E");
        hv5.setOrientation(LinearLayout.HORIZONTAL);

        new WeightUtil(context).mLayoutParams_Btn(btnHv6);

        hv6.setProgress(0.6);
        new WeightUtil(context).mLayoutParams_View(hv6);
        hv6.setRateBackgroundColor("#EACFA7");
        hv6.setOrientation(LinearLayout.HORIZONTAL);


    }

    class ViewWrapper{
        private View mTargert;

        public ViewWrapper(View mTargert){
            this.mTargert=mTargert;
        }

        public int getWidth(){
            return mTargert.getLayoutParams().width;
        }

        public void setWidth(int width){
            mTargert.getLayoutParams().width=width;
            mTargert.requestLayout();
        }

        public int getHeight(){
            return mTargert.getLayoutParams().height;
        }

        public void setHeight(int height){
            mTargert.getLayoutParams().height=height;
            mTargert.requestLayout();
        }
    }
}
