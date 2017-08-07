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
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;

import com.yw.obd.util.ScreenUtils;

public class FhistogramView extends View {
	private static final String TAG = FhistogramView.class.getSimpleName();

	private Paint yLinePaint;// 坐标轴竖直内部 虚线画笔
	// 坐标轴底部的星期数
	private String[] xWeeks;

	private Paint xLinePaint;// 坐标轴 轴线 画笔：
	private Paint hLinePaint;// 坐标轴水平内部 虚线画笔
	private Paint titlePaint;// 绘制文本的画笔
	private int screenWidth;
	private int screenHeight;

	public FhistogramView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public FhistogramView(Context context) {
		super(context);
		init(context, null);
	}

	private void init(Context context, AttributeSet attrs) {
		screenWidth = ScreenUtils.getScreenWidth(getContext());
		screenHeight = ScreenUtils.getScreenHeight(getContext());
		xWeeks = new String[] { "000", "100", "200", "300", "400", "500",
				"600", "700" };
		xLinePaint = new Paint();
		hLinePaint = new Paint();
		yLinePaint = new Paint();
		titlePaint = new Paint();
		yLinePaint.setColor(Color.BLACK);
		xLinePaint.setColor(Color.BLACK);
		hLinePaint.setColor(Color.BLACK);
		titlePaint.setColor(Color.BLACK);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getWidth();

		canvas.drawLine((float) (screenWidth * 0.08),
				(float) (screenWidth * 0.1), (float) (screenWidth * 0.08),
				(float) (0.6 * screenHeight), xLinePaint);

		canvas.drawLine((float) (screenWidth * 0.08),
				(float) (screenWidth * 0.1), width - 10,
				(float) (screenWidth * 0.1), xLinePaint);

		// 3 绘制 Y轴 坐标

		titlePaint.setTextAlign(Align.RIGHT);
		titlePaint.setTextSize(40);
		titlePaint.setAntiAlias(false);
		titlePaint.setStyle(Paint.Style.FILL);
		int xPerWidth = screenWidth / 9;// 分成四部分

		yLinePaint.setTextAlign(Align.CENTER);
		for (int i = 0; i < 8; i++) {
			canvas.drawLine(i * xPerWidth + (float) (screenWidth * 0.08),
					(float) (screenWidth * 0.1), i * xPerWidth
							+ (float) (screenWidth * 0.08), (float) (0.6 * screenHeight), yLinePaint);
			System.out.println("sss---1----->"+(float) (screenWidth * 0.78));
		}

		// 4 绘制 X 轴坐标
		int xAxisLength = getWidth();
		int columCount = xWeeks.length + 1;
		int step = xAxisLength / 9;
		for (int i = 0; i < columCount - 1; i++) {
			titlePaint.setStrokeWidth(10);
			canvas.drawText(xWeeks[i], step * (i)
					+ (float) (screenWidth * 0.12),
					(float) (screenWidth * 0.07), titlePaint);
			System.out.println("wwww---------->" +( step * (i)
					+ (float) (screenWidth * 0.12)));
		}

	}
}
