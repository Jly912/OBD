package com.yw.obd.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yw.obd.R;

import butterknife.ButterKnife;

/**
 * Created by apollo on 2017/7/25.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private FragmentManager manager;

    private BaseFragment fragment;

    private boolean mIsFullScreen = false;
    private boolean isStatusBarTranslate = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        manager = getSupportFragmentManager();
        initStatusBar();

        init();
    }

    protected abstract int getLayoutId();

    public boolean isOpenStatus() {
        return isStatusBarTranslate;
    }


    /**
     * 获得通知栏的高度
     *
     * @return
     */
    private int getStatusHeight(Context context) {
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            return context.getResources().getDimensionPixelSize(resId);
        }
        return -1;
    }


    /**
     * 展示Fragment
     */
    protected void showFragment(int resid, BaseFragment fragment) {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        //隐藏正在暂时的Fragment
        if (this.fragment != null) {
            fragmentTransaction.hide(this.fragment);
        }

        //展示需要显示的Fragment对象
        Fragment mFragment = manager.findFragmentByTag(fragment.getClass().getName());
        if (mFragment != null) {
            fragmentTransaction.show(mFragment);
            this.fragment = (BaseFragment) mFragment;
        } else {
            fragmentTransaction.add(resid, fragment, fragment.getClass().getName());
            this.fragment = fragment;
        }

        fragmentTransaction.commit();
    }


    protected void init() {
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }


    /**
     * 初始化状态栏相关，
     * PS: 设置全屏需要在调用super.onCreate(arg0);之前设置setIsFullScreen(true);否则在Android 6.0下非全屏的activity会出错;
     * SDK19：可以设置状态栏透明，但是半透明的SYSTEM_BAR_BACKGROUNDS会不好看；
     * SDK21：可以设置状态栏颜色，并且可以清除SYSTEM_BAR_BACKGROUNDS，但是不能设置状态栏字体颜色（默认的白色字体在浅色背景下看不清楚）；
     * SDK23：可以设置状态栏为浅色（SYSTEM_UI_FLAG_LIGHT_STATUS_BAR），字体就回反转为黑色。
     * 为兼容目前效果，仅在SDK23才显示沉浸式。
     */
    private void initStatusBar() {
        Window win = getWindow();
        if (mIsFullScreen) {
            win.requestFeature(Window.FEATURE_NO_TITLE);
            win.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
            win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 保持屏幕高亮
        } else {
            if (isStatusBarTranslate) {
                //KITKAT也能满足，只是SYSTEM_UI_FLAG_LIGHT_STATUS_BAR（状态栏字体颜色反转）只有在6.0才有效
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
                    // 状态栏字体设置为深色，SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 为SDK23增加
                    win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                    // 部分机型的statusbar会有半透明的黑色背景
                    win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    win.setStatusBarColor(getResources().getColor(R.color.tab));// SDK21

                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    win.setStatusBarColor(getResources().getColor(R.color.tool_color));

                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }

                int statusHeight = getStatusHeight(this);
                if (statusHeight != -1) {

                    View view = findViewById(R.id.actionbar);
                    if (view != null) {
                        view.setPadding(0, statusHeight, 0, 0);
                    }
                }

            }

        }
    }
}
