package com.yw.obd.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.fragment.CarFragment;
import com.yw.obd.fragment.FuelFragment;
import com.yw.obd.fragment.HomeFragment;
import com.yw.obd.fragment.MineFragment;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.fl_zw)
    FrameLayout flZw;
    @Bind(R.id.rb_home)
    RadioButton rbHome;
    @Bind(R.id.rb_car)
    RadioButton rbCar;
    @Bind(R.id.rb_oil)
    RadioButton rbOil;
    @Bind(R.id.rb_mine)
    RadioButton rbMine;
    @Bind(R.id.rg_home)
    RadioGroup rgHome;

    private CarFragment carFragment;
    private FuelFragment fuelFragment;
    private HomeFragment homeFragment;
    private MineFragment mineFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        mineFragment = MineFragment.getInstance();
        carFragment = CarFragment.getInstance();
        homeFragment = HomeFragment.getInstance();
        fuelFragment = FuelFragment.getInstance();

        rgHome.getChildAt(0).performClick();//模拟点击
        rgHome.setOnCheckedChangeListener(this);
        showFragment(R.id.fl_zw, new HomeFragment());
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                rbHome.setTextColor(getResources().getColor(R.color.btn_blue));
                rbCar.setTextColor(getResources().getColor(R.color.board_gray));
                rbOil.setTextColor(getResources().getColor(R.color.board_gray));
                rbMine.setTextColor(getResources().getColor(R.color.board_gray));

                showFragment(R.id.fl_zw, homeFragment);
                break;
            case R.id.rb_car:
                rbCar.setTextColor(getResources().getColor(R.color.btn_blue));
                rbHome.setTextColor(getResources().getColor(R.color.board_gray));
                rbOil.setTextColor(getResources().getColor(R.color.board_gray));
                rbMine.setTextColor(getResources().getColor(R.color.board_gray));
                showFragment(R.id.fl_zw, carFragment);
                break;
            case R.id.rb_oil:
                rbOil.setTextColor(getResources().getColor(R.color.btn_blue));
                rbCar.setTextColor(getResources().getColor(R.color.board_gray));
                rbHome.setTextColor(getResources().getColor(R.color.board_gray));
                rbMine.setTextColor(getResources().getColor(R.color.board_gray));
                showFragment(R.id.fl_zw, fuelFragment);
                break;
            case R.id.rb_mine:
                rbMine.setTextColor(getResources().getColor(R.color.btn_blue));
                rbCar.setTextColor(getResources().getColor(R.color.board_gray));
                rbOil.setTextColor(getResources().getColor(R.color.board_gray));
                rbHome.setTextColor(getResources().getColor(R.color.board_gray));
                showFragment(R.id.fl_zw, mineFragment);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.sure_exit)
                    .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            MainActivity.this.finish();
                        }
                    }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
            return true;
        }
        return true;
    }
}
