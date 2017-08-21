package com.yw.obd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.yw.obd.R;

/**
 * Created by apollo on 2017/8/18.
 */

public class SplashActivity extends AppCompatActivity {

    private ImageView iv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        iv = (ImageView) findViewById(R.id.iv);
//        //加载动画
//        Animation ani = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.left_out_right_in);
//        //使用ImageView显示动画
//        iv.startAnimation(ani);
        iv.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 3000);
    }
}
