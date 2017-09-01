package com.yw.obd.activity;

import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yw.obd.R;
import com.yw.obd.adapter.CheckAdapter;
import com.yw.obd.adapter.RyAdapter;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.entity.ErrorCode;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;
import com.yw.obd.widget.BarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    @Bind(R.id.ry)
    RecyclerView ry;
    @Bind(R.id.btn_Clear)
    Button btnClear;
    @Bind(R.id.ll)
    LinearLayout ll;
    @Bind(R.id.ivScan)
    ImageView ivScan;
    private boolean isLoad = true;
    private int value = 0;
    private int total = 20;
    private RunThread runThread;
    private String id, name;
    private CheckAdapter adapter;
    private RyAdapter adap;
    private List<ErrorCode> err;
    private boolean isRun;
    private int itemNum;
    private static final int MSG_RUN_ITEM = 0x00;
    private static final int MSG_RUN_ADD = 0x01;
    private static final int MSG_RUN_GONE = 0x02;
    private int commandResponse;
    private int getResponseTime = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_RUN_ITEM) {
                //滚动中
                if (itemNum <= 0) {
                    itemNum = adap.getItemCount();
                    ry.smoothScrollToPosition(0);
                } else {
                    ry.smoothScrollToPosition(itemNum);
                }

            } else if (msg.what == MSG_RUN_ADD) {//滚到第一个了
                Log.e("print", "----");
                ry.smoothScrollToPosition(adap.getItemCount());
                if (itemNum <= 0) {
                    ry.smoothScrollToPosition(0);
                    itemNum = adap.getItemCount();
                } else {
                    ry.smoothScrollToPosition(itemNum);
                }
            } else if (msg.what == MSG_RUN_GONE) {
                ry.setVisibility(View.GONE);
            }
        }
    };

    private Handler getResponseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getResponse(commandResponse);

        }
    };

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
        Log.e("print", "id" + id + "name" + name);
        String json = initError();
        TypeToken<List<ErrorCode>> tt = new TypeToken<List<ErrorCode>>() {
        };
        err = new Gson().fromJson(json, tt.getType());
        runThread = new RunThread();
        ry.setFocusable(false);

        if (err != null) {
            ry.setLayoutManager(new LinearLayoutManager(this));
            adap = new RyAdapter(this);
            adap.setData(err);
            ry.setAdapter(adap);
            ry.scrollToPosition(adap.getItemCount() - 1);
        }
        isRun = true;
//        itemNum = 5;
        itemNum = adap.getItemCount();

        startAnim();
        runBar();
    }

    private String initError() {
        AssetManager assets = getAssets();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(assets.open("carCode.json")));
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 开始动画
     */
    private void startAnim() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.roa);
        LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
        animation.setInterpolator(interpolator);
        if (animation != null) {
            ivScan.startAnimation(animation);  //开始动画
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 循环运行bar
     */
    private void runBar() {
        runThread.start();
        thread.start();
    }

    Thread thread = new Thread() {
        @Override
        public void run() {
            while (isRun) {
                if (itemNum >= 0) {
                    handler.sendEmptyMessage(MSG_RUN_ADD);
                } else {
                    handler.sendEmptyMessage(MSG_RUN_ITEM);
                }
                try {
                    Thread.sleep(120);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                itemNum -= 4;
            }
        }
    };


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
                            isRun = false;
                            handler.sendEmptyMessage(MSG_RUN_GONE);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    lv.setVisibility(View.VISIBLE);
                                    Http.checkCar(CarCheckActivity.this, id, new Http.OnListener() {
                                        @Override
                                        public void onSucc(Object object) {
                                            String res = (String) object;
                                            try {
                                                JSONObject jsonObject = new JSONObject(res);
                                                int state = Integer.parseInt(jsonObject.getString("state"));
                                                switch (state) {
                                                    case 0:
                                                        ivScan.clearAnimation();
                                                        ivScan.setVisibility(View.GONE);
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
                                                        ll.setVisibility(View.VISIBLE);
                                                        initList(codes, strs, name);

                                                        break;
                                                    case 2011:
                                                        ivLine.clearAnimation();
                                                        ivScan.clearAnimation();
                                                        ivScan.setVisibility(View.GONE);
                                                        tvCheck.setText(R.string.check_succ);
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

    @OnClick({R.id.iv_back, R.id.btn_Clear})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                isRun = false;
                isLoad = false;
                finish();
                break;
            case R.id.btn_Clear:
                clearCode();
                break;
        }


    }

    private String commandType = "8415";

    private void clearCode() {
        Http.sendCommand(this, AppData.GetInstance(this).getSN(), AppData.GetInstance(this).getSelectedDevice() + "", commandType, 38 + "",
                "", new Http.OnListener() {
                    @Override
                    public void onSucc(Object object) {
                        if (object != null) {
                            String res = (String) object;
                            int state = Integer.parseInt(res);
                            switch (state) {
                                case -1:
                                    AppData.showToast(CarCheckActivity.this, R.string.device_notexist);
                                    break;
                                case -2:
                                    AppData.showToast(CarCheckActivity.this, R.string.device_offline);
                                    break;
                                case -3:
                                    AppData.showToast(CarCheckActivity.this, R.string.command_send_failed);
                                    break;
                                case -5:
                                    AppData.showToast(CarCheckActivity.this, R.string.commandsave);
                                    break;
                                default:
                                    commandResponse = state;
                                    AppData.showToast(CarCheckActivity.this, R.string.commandsendsuccess);
                                    getResponse(commandResponse);
                                    break;
                            }

                        }
                    }
                });
    }

    private void getResponse(int commandId) {
        ++getResponseTime;
        Http.getResponse(this, commandId, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (object != null) {
                    String res = (String) object;
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        int state = jsonObject.getInt("state");
                        switch (state) {
                            case 0:
                                int isResponse = jsonObject.getInt("isResponse");
                                String responseMsg = jsonObject.getString("responseMsg");
                                if (isResponse == 0) {
                                    if (getResponseTime < 3) {
                                        getResponseHandler.sendEmptyMessageDelayed(0, 5000);
                                    } else {
                                        getResponseTime = 0;
                                        AppData.showToast(CarCheckActivity.this, R.string.commandsendtimeout);
                                    }

                                } else if (isResponse == 1) {
                                    AppData.showToast(CarCheckActivity.this, R.string.setSucc);
                                    finish();
                                }
                                break;
                            default:
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (isLoad) {
                isLoad = false;
            }

            if (isRun) {
                isRun = false;
            }
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
