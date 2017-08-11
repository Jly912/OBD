package com.yw.obd.activity;

import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.adapter.CheckAdapter;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;
import com.yw.obd.widget.BarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/8/10.
 */

public class CarCheckActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_line)
    ImageView ivLine;
    @Bind(R.id.bBar)
    BarView bvBar;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.tv_check)
    TextView tvCheck;
    private boolean isLoad = true;
    private int value = 0;
    private int total = 20;
    private RunThread runThread;
    private String id, name;
    private CheckAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_car;
    }

    @Override
    protected void init() {
        tvTitle.setText(R.string.checkCar);
        ivBack.setVisibility(View.VISIBLE);
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        runThread = new RunThread();
        startAnim();
        runBar();

    }

    /**
     * 开始动画
     */
    private void startAnim() {
        //xstart 0 ; xend 0; ystart 0; yend 800;  坐标
        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, 400);
        //每一次循环的时间
        anim.setDuration(2000);
        //结束时候状态
        anim.setFillAfter(true);
        //重复的方式
        anim.setRepeatMode(Animation.REVERSE);
        //重复的次数
        anim.setRepeatCount(Animation.INFINITE);
        //开始动画
        ivLine.startAnimation(anim);
    }


    /**
     * 循环运行bar
     */
    private void runBar() {
        runThread.start();
    }


    class RunThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (isLoad) {
                if (bvBar != null) {
                    if (value < total) {
                        value++;
                        bvBar.setVolume(value, total);
                        if (value == total) {
                            isLoad = false;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Http.checkCar(CarCheckActivity.this, id, new Http.OnListener() {
                                        @Override
                                        public void onSucc(Object object) {
                                            String res = (String) object;
                                            try {
                                                JSONObject jsonObject = new JSONObject(res);
                                                int state = Integer.parseInt(jsonObject.getString("state"));
                                                switch (state) {
                                                    case 0:
                                                        ivLine.clearAnimation();
                                                        tvCheck.setText(R.string.check_succ);
                                                        AppData.showToast(CarCheckActivity.this, R.string.check_succ);
                                                        String gzTime = jsonObject.getString("gzTime");
                                                        JSONArray arr = jsonObject.getJSONArray("arr");
                                                        List<String> codes = new ArrayList<>();
                                                        List<String> strs = new ArrayList<>();
                                                        for (int i = 0; i < arr.length(); i++) {
                                                            JSONObject jo = arr.getJSONObject(i);
                                                            String code = jo.getString("code");
                                                            codes.add(code);
                                                            String str = jo.getString("str");
                                                            strs.add(str);
                                                        }

                                                        initList(codes, strs, name);

                                                        break;
                                                    case 2011:
                                                        break;
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                        Log.e("print", "value" + value);
                    } else {
                        value = 0;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void initList(List<String> codes, List<String> strs, String name) {
        AnimationSet set = new AnimationSet(false);
        Animation animation = new AlphaAnimation(0, 1);   //AlphaAnimation 控制渐变透明的动画效果
        animation.setDuration(500);     //动画时间毫秒数
        set.addAnimation(animation);    //加入动画集合

        animation = new TranslateAnimation(1, 13, 10, 50);  //ScaleAnimation 控制尺寸伸缩的动画效果
        animation.setDuration(300);
        set.addAnimation(animation);

        animation = new RotateAnimation(30, 10);    //TranslateAnimation  控制画面平移的动画效果
        animation.setDuration(300);
        set.addAnimation(animation);

        animation = new ScaleAnimation(5, 0, 2, 0);    //RotateAnimation  控制画面角度变化的动画效果
        animation.setDuration(300);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 1);
        lv.setLayoutAnimation(controller);

        adapter = new CheckAdapter(this, codes, strs, name);
        lv.setAdapter(adapter);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        isLoad = false;
        finish();
    }
}