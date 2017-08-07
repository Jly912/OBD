package com.yw.obd.util;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public class WeightUtil {
	private int screenHeight;
	private int screenWidth;
	private static WeightUtil uniqueInstance = null;

	public WeightUtil() {
	}

	public static WeightUtil getInstance(Context context) {
		if (uniqueInstance == null) {
			uniqueInstance = new WeightUtil(context);
		}
		return uniqueInstance;
	}

	public WeightUtil(Context context) {
		screenHeight = ScreenUtils.getScreenHeight(context);
		screenWidth = ScreenUtils.getScreenWidth(context);
	}

	public void mLayoutParams_View(View view) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				(int) (screenWidth * 0.78), (int) (screenHeight * 0.04));
		// System.out.println("wid---->"+(int) (screenWidth * 0.78 * d));
		params.setMargins(0, 0, 0, 0);
		view.setLayoutParams(params);
	}

	public void mLayoutParams_Text(View view, double d) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				(int) (screenHeight * 0.04));
		params.setMargins((-1) * ((int) (screenWidth * 0.72* (1 - d))), 0, 0,
				0);
		view.setLayoutParams(params);
	}

	public void mLayoutParams_Btn(View view) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				(int) (screenWidth * 0.08), (int) (screenHeight * 0.04));
		params.setMargins(0, 0, 0, 0);
		view.setLayoutParams(params);
	}

}
