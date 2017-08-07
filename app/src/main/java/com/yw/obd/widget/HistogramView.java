/**
 * 工程名: histogram
 * 文件名: HistogramView.java
 * 包名: com.lyx.histogram
 * 日期: 2014-4-21下午8:23:38
 * Copyright (c) 2014
 *
 */

package com.yw.obd.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.yw.obd.util.ScreenUtils;

public class HistogramView extends View implements Runnable {

	private int comHeight;// 控件高度

	private View rateView;// 进度条

	private String rateBackgroundColor;// 进图条背景颜色

	private int rateBackgroundId; // 进图条背景图片id

	private int rateHeight; // 进度条的高

	private int rateWidth; // 进度条的宽

	private int rateAnimValue;// 进度条动画高度

	private int orientation; // 设置柱状图方向

	private double progress;// 设置进度 1为最大值

	private boolean isAnim = true; // 是否动画显示统计条

	private Handler handler = new Handler();// 动画handler

	private int animRate = 20; // 动画速度 以每1毫秒计

	private int animTime = 1;// 动画延迟执行时间

	private Canvas canvas;// 画布
	private int screenHeight;
	private int screenWidth;

	public HistogramView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HistogramView(Context context) {
		super(context);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 初始化控件和进度的条相关参数
		comHeight = h;
		if (orientation == LinearLayout.HORIZONTAL) {
			rateWidth = (int) (w * progress);
			rateHeight = h;
		} else {
			rateHeight = (int) (h * progress);
			rateWidth = w;
			System.out.println("w------------->" + w);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.canvas = canvas;
		screenHeight = ScreenUtils.getScreenHeight(getContext());
		screenWidth = ScreenUtils.getScreenWidth(getContext());
//		System.out.println("screenHeight----------->"+screenHeight);
//		System.out.println("screenWidth----------->"+screenWidth);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		if (rateBackgroundColor != null) {
			drawViewWithColor(paint, isAnim);
		}
	}

	/**
	 * 
	 * drawViewWithColor:(绘制颜色进度条).
	 */
	private void drawViewWithColor(Paint paint, boolean isAnim) {
		paint.setColor(Color.parseColor(rateBackgroundColor));
		if (isAnim) {
			handler.postDelayed(this, animTime);
			if (orientation == LinearLayout.HORIZONTAL) {// 水平方向柱状图
				canvas.drawRect(0, 0, rateAnimValue, comHeight, paint);
			}
		}

	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public View getRateView() {
		return rateView;
	}

	public void setRateView(View rateView) {
		this.rateView = rateView;
	}

	public int getRateHeight() {
		return rateHeight;
	}

	public void setRateHeight(int rateHeight) {
		this.rateHeight = rateHeight;
	}

	public int getRateWidth() {
		return rateWidth;
	}

	public void setRateWidth(int rateWidth) {
		this.rateWidth = rateWidth;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public boolean isAnim() {
		return isAnim;
	}

	public void setAnim(boolean isAnim) {
		this.isAnim = isAnim;
	}

	public int getAnimRate() {
		return animRate;
	}

	public void setAnimRate(int animRate) {
		this.animRate = animRate;
	}

	public String getRateBackgroundColor() {
		return rateBackgroundColor;
	}

	public void setRateBackgroundColor(String rateBackgroundColor) {
		this.rateBackgroundColor = rateBackgroundColor;
		rateBackgroundId = -1;
	}

	public int getRateBackgroundId() {
		return rateBackgroundId;
	}

	public void setRateBackgroundId(int rateBackground) {
		this.rateBackgroundId = rateBackground;
		rateBackgroundColor = null;
	}

	/**
	 * 
	 * 刷新界面
	 * 
	 * @see Runnable#run()
	 */
	@Override
	public void run() {
		if (orientation == LinearLayout.HORIZONTAL
				&& (rateAnimValue <= rateWidth)) {
			rateAnimValue += animRate;
			invalidate();
		} else if (orientation == LinearLayout.VERTICAL
				&& (rateAnimValue <= rateHeight)) {
			rateAnimValue += animRate;
			invalidate();
		} else {
			handler.removeCallbacks(this);
			rateAnimValue = 0;
		}

	}

}
