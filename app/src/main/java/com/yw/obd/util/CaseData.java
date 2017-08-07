package com.yw.obd.util;

import com.yw.obd.R;

public class CaseData {
	public static int returnIconInt(int angle, int statusInt) {
		int ID= R.drawable.offline_0;
		if (statusInt == 3 || statusInt == 0 || statusInt == 4) {
			if (angle>=0&&angle<=22.5||angle>337.5) {
				ID = R.drawable.offline_0;
			}else if (angle>22.5&&angle<=67.5) {
				ID = R.drawable.offline_45;
			}else if (angle>67.5&&angle<=112.5) {
				ID= R.drawable.offline_90;
			}else if (angle>112.5&&angle<=157.5) {
				ID = R.drawable.offline_135;
			}else if (angle>157.5&&angle<=202.5) {
				ID = R.drawable.offline_180;
			}else if (angle>202.5&&angle<=247.5) {
				ID = R.drawable.offline_225;
			}else if (angle>247.5&angle<=292.5) {
				ID = R.drawable.offline_270;
			}else {
				ID = R.drawable.offline_315;
			}
		} else if(statusInt == 1) {
			if (angle>=0&&angle<=22.5||angle>337.5) {
				ID = R.drawable.driving_0;
			}else if (angle>22.5&&angle<=67.5) {
				ID = R.drawable.driving_45;
			}else if (angle>67.5&&angle<=112.5) {
				ID = R.drawable.driving_90;
			}else if (angle>112.5&&angle<=157.5) {
				ID = R.drawable.driving_135;
			}else if (angle>157.5&&angle<=202.5) {
				ID = R.drawable.driving_180;
			}else if (angle>202.5&&angle<=247.5) {
				ID = R.drawable.driving_225;
			}else if (angle>247.5&angle<=292.5) {
				ID = R.drawable.driving_270;
			}else {
				ID = R.drawable.driving_315;
			}
		}else  {
			if (angle>=0&&angle<=22.5||angle>337.5) {
				ID = R.drawable.online_0;
			}else if (angle>22.5&&angle<=67.5) {
				ID = R.drawable.online_45;
			}else if (angle>67.5&&angle<=112.5) {
				ID = R.drawable.online_90;
			}else if (angle>112.5&&angle<=157.5) {
				ID = R.drawable.online_135;
			}else if (angle>157.5&&angle<=202.5) {
				ID = R.drawable.online_180;
			}else if (angle>202.5&&angle<=247.5) {
				ID = R.drawable.online_225;
			}else if (angle>247.5&angle<=292.5) {
				ID = R.drawable.online_270;
			}else {
				ID = R.drawable.online_315;
			}
		}
		return ID;
	}
	public static int returnCourse(int angle) {
			if (angle>=0&&angle<=22.5||angle>337.5) {
				return R.string.course_N;
			}else if (angle>22.5&&angle<=67.5) {
				return R.string.course_EN;
			}else if (angle>67.5&&angle<=112.5) {
				return R.string.course_E;
			}else if (angle>112.5&&angle<=157.5) {
				return R.string.course_ES;
			}else if (angle>157.5&&angle<=202.5) {
				return R.string.course_S;
			}else if (angle>202.5&&angle<=247.5) {
				return R.string.course_WS;
			}else if (angle>247.5&angle<=292.5) {
				return R.string.course_W;
			}else {
				return R.string.course_WN;
			}
	}
}
