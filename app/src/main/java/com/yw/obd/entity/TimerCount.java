package com.yw.obd.entity;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.yw.obd.R;

/**
 * Created by apollo on 2017/8/3.
 */

public class TimerCount extends CountDownTimer {

    private TextView tv;
    private Context context;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public TimerCount(long millisInFuture, long countDownInterval, TextView tv, Context context) {
        super(millisInFuture, countDownInterval);
        this.tv = tv;
        this.context = context;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        //计时过程显示
        tv.setEnabled(false);
        tv.setClickable(false);
        tv.setPressed(false);
        tv.setTextColor(context.getResources().getColor(R.color.board_gray));
        tv.setText(millisUntilFinished/1000+"秒");
    }

    @Override
    public void onFinish() {
        //计时完毕触发
        tv.setClickable(true);
        tv.setEnabled(true);
        tv.setTextColor(context.getResources().getColor(R.color.btn_blue));
        tv.setText(R.string.send_code);
    }
}
