package com.yw.obd.util;

import android.text.TextUtils;

import com.yw.obd.R;
import com.yw.obd.app.AppContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class TimeUtils {
	public static String DateConversionUtilA(String date) {
		String dates = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy��MM��dd��");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MMM d, yyyy",
				Locale.ENGLISH);
		if (AppContext.getContext().getResources().getConfiguration().locale
				.getCountry().equals("CN")) {
			try {
				dates = sdf1.format(sdf.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			try {
				dates = sdf2.format(sdf.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return dates;
	}

	public static String DateConversionUtilAA(String date) {
		String dates = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy��MM��dd��");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MMM d, yyyy",
				Locale.ENGLISH);
		if (AppContext.getContext().getResources().getConfiguration().locale
				.getCountry().equals("CN")) {
			try {
				dates = sdf.format(sdf1.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			try {
				dates = sdf.format(sdf2.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return dates;
	}

	public static String DateConversionUtilA(int year, int month, int day) {
		String dates = "";
		dates = DateConversionUtilD(year, month, day);
		dates = DateConversionUtilA(dates);
		return dates;
	}

	// int hh int mm ת hh:mm
	public static String DateConversionUtilA(int hour, int minute) {
		String dates = "";
		if (hour < 10) {
			dates = dates + "0" + String.valueOf(hour) + ":";
		} else {
			dates = dates + String.valueOf(hour) + ":";
		}

		if (minute >= 10) {
			dates = dates + String.valueOf(minute);
		} else {
			dates = dates + "0" + String.valueOf(minute);
		}
		return dates;
	}

	public static String DateConversionUtilA(Date date) {
		String dates = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy��MM��dd��");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MMM d, yyyy",
				Locale.ENGLISH);
		if (AppContext.getContext().getResources().getConfiguration().locale
				.getCountry().equals("CN")) {
			dates = sdf1.format(date);
		} else {
			dates = sdf2.format(date);
		}
		return dates;
	}

	public static String DateConversionUtilE(Date date) {
		String dates = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd HH:mm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm MMM d",
				Locale.ENGLISH);
		if (AppContext.getContext().getResources().getConfiguration().locale
				.getCountry().equals("CN")) {
			dates = sdf1.format(date);
		} else {
			dates = sdf2.format(date);
		}
		return dates;
	}

	public static String DateConversionUtilF(Date date) {
		String dates = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm MMM d, yyyy",
				Locale.ENGLISH);
		if (AppContext.getContext().getResources().getConfiguration().locale
				.getCountry().equals("CN")) {
			dates = sdf1.format(date);
		} else {
			dates = sdf2.format(date);
		}
		return dates;
	}

	public static String DateConversionUtilF(String date) {
		String dates = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			dates = DateConversionUtilF(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dates;
	}

	public static String DateConversionUtilB(String date) {
		String dates = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy��MM��");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MMM, yyyy",
				Locale.ENGLISH);
		if (AppContext.getContext().getResources().getConfiguration().locale
				.getCountry().equals("CN")) {
			try {
				dates = sdf1.format(sdf.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			try {
				dates = sdf2.format(sdf.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return dates;
	}

	public static int[] DateConversionUtilC(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String[] strs = date.split("/");
		int[] mDate;
		mDate = new int[] { Integer.valueOf(strs[0]), Integer.valueOf(strs[1]),
				Integer.valueOf(strs[2]) };
		return mDate;
	}

	// dateתyyyy/mm/dd
	public static String DateConversionUtilC(Date date) {
		String dates = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		dates = sdf.format(date);
		return dates;
	}

	// int yyyy int mm int dd ת yyyy/MM/dd
	public static String DateConversionUtilD(int year, int month, int day) {
		String dates = "";
		dates = String.valueOf(year) + "/";
		if (month < 10) {
			dates = dates + "0" + String.valueOf(month) + "/";
		} else {
			dates = dates + String.valueOf(month) + "/";
		}

		if (day >= 10) {
			dates = dates + String.valueOf(day);
		} else {
			dates = dates + "0" + String.valueOf(day);
		}
		return dates;
	}

	public static String TimeChange(String time_a, String time_b) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			Date date_a = sdf.parse(time_a);
			if (TextUtils.isEmpty(time_b)) {
				return TimeDeferent(date_a);
			}
			Date date_b = sdf.parse(time_b);
			if (date_a.getYear() == date_b.getYear()) {
				if (date_a.getMonth() == date_b.getMonth()) {
					if (date_a.getDay() == date_b.getDay()) {

						if (date_a.getHours() == date_b.getHours()) {
							if (date_a.getMinutes() == date_b.getMinutes()) {
								return "";
							} else if (date_a.getMinutes()
									- date_b.getMinutes() > 3) {
								return "";
							} else {
								return TimeDeferent(date_a);
							}
						} else {
							return TimeDeferent(date_a);
						}
					} else {
						return TimeDeferent(date_a);
					}
				} else {
					return TimeDeferent(date_a);
				}
			} else {
				return TimeDeferent(date_a);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	private static String TimeDeferent(Date date_a) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int monthOfYear = calendar.get(Calendar.MONTH) + 1;
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		int minuteOfHour = calendar.get(Calendar.MINUTE);
		Calendar calendar_a = Calendar.getInstance();
		calendar_a.setTime(date_a);
		int year_a = calendar_a.get(Calendar.YEAR);
		int monthOfYear_a = calendar_a.get(Calendar.MONTH) + 1;
		int dayOfMonth_a = calendar_a.get(Calendar.DAY_OF_MONTH);
		int hourOfDay_a = calendar_a.get(Calendar.HOUR_OF_DAY);
		int minuteOfHour_a = calendar_a.get(Calendar.MINUTE);
		/*
		 * System.out.println(dayOfMonth+"  "+monthOfYear+"  "+ year);
		 * System.out.println(dayOfMonth_a+"  "+monthOfYear_a+"  "+ year_a);
		 */
		if (year_a == year) {
			if (monthOfYear_a == monthOfYear) {
				if (dayOfMonth_a == dayOfMonth) {
					/*
					 * System.out.println(DateConversionUtilA(date_a.getHours(),
					 * date_a.getMinutes()));
					 */
					return DateConversionUtilA(date_a.getHours(),
							date_a.getMinutes());
				} else {
					return DateConversionUtilE(date_a);
				}
			} else {
				return DateConversionUtilE(date_a);
			}
		} else {
			return DateConversionUtilF(date_a);
		}
	}

	public static String getToday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	public static String getBefore(int i) {
		if (i == 0)
			return getToday();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, i);
		Date dBefore = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(dBefore);
	}

	public static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(new Date());
	}

	public static boolean timeComparison(String time_a, String time_b) {
		if (TextUtils.isEmpty(time_a) || TextUtils.isEmpty(time_b)) {
			return true;
		}
		if (time_a.equals(time_b)) {
			return false;
		}
		int hour_a = Integer.valueOf(time_a.split(":")[0]), minute_a = Integer
				.valueOf(time_a.split(":")[1]), hour_b = Integer.valueOf(time_b
				.split(":")[0]), minute_b = Integer
				.valueOf(time_b.split(":")[1]);
		if (hour_a < hour_b) {
			return true;
		} else if (hour_a == hour_b) {
			if (minute_a < minute_b) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean DataComparison(String time_a, String time_b) {
		if (TextUtils.isEmpty(time_a) || TextUtils.isEmpty(time_b)) {
			return false;
		}
		if (time_a.equals(time_b)) {
			return true;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date d1;
		try {
			d1 = sdf.parse(time_a);
			Date d2 = sdf.parse(time_b);
			long diff = d1.getTime() - d2.getTime();
			if (diff < 0)
				return true;
			else
				return false;
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return true;
		}
	}

	public static String converTime(String srcTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat dspFmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String convertTime;

		Date result_date;
		long result_time = 0;

		if (null == srcTime) {
			result_time = System.currentTimeMillis();
		} else {
			try {
				sdf.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
				result_date = sdf.parse(srcTime);

				result_time = result_date.getTime();
			} catch (Exception e) {
				result_time = System.currentTimeMillis();
				dspFmt.setTimeZone(TimeZone.getDefault());
				convertTime = dspFmt.format(result_time);
				return convertTime;
			}
		}
		Calendar nowCal = Calendar.getInstance();
		TimeZone localZone = nowCal.getTimeZone();
		dspFmt.setTimeZone(localZone);
		convertTime = dspFmt.format(result_time);

		return convertTime;
	}

	public static String converToUTCTime(String srcTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String convertTime;

		Date result_date;
		long result_time = 0;

		if (null == srcTime) {
			result_time = System.currentTimeMillis();
		} else {
			try {
				sdf.setTimeZone(TimeZone.getDefault());
				result_date = sdf.parse(srcTime);
				result_time = result_date.getTime();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				result_time = System.currentTimeMillis();
				sdf.setTimeZone(TimeZone.getDefault());
				convertTime = sdf.format(result_time);
				return convertTime;
			}

		}
		sdf.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
		convertTime = sdf.format(result_time);

		return convertTime;
	}

	/**
	 * 
	 * @param min
	 * @return day hour min
	 */
	public static String minConvertDayHourMin(Double min) {
		String html = "0"
				+ AppContext.getContext().getResources()
						.getString(R.string.minute);
		if (min != null) {
			Double m = (Double) min;
			String format;
			Object[] array;
			Integer days = (int) (m / (60 * 24));
			Integer hours = (int) (m / (60) - days * 24);
			Integer minutes = (int) (m - hours * 60 - days * 24 * 60);
			if (days > 0) {
				format = "%1$,d"
						+AppContext.getContext().getResources()
								.getString(R.string.day)
						+ "%2$,d"
						+ AppContext.getContext().getResources()
								.getString(R.string.hour)
						+ "%3$,d"
						+ AppContext.getContext().getResources()
								.getString(R.string.minute);

				array = new Object[] { days, hours, minutes };
			} else if (hours > 0) {
				format = "%1$,d"
						+ AppContext.getContext().getResources()
								.getString(R.string.hour)
						+ "%2$,d"
						+ AppContext.getContext().getResources()
								.getString(R.string.minute);
				array = new Object[] { hours, minutes };
			} else {
				format = "%1$,d"
						+ AppContext.getContext().getResources()
								.getString(R.string.minute);
				array = new Object[] { minutes };
			}
			html = String.format(format, array);
		}

		return html;
	}

	/**
	 * @param time_a
	 *            , time_b
	 * @return time_a - time_b
	 */
	public static long Differ(String time_a, String time_b) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d1;
		try {
			d1 = sdf.parse(time_a);
			Date d2 = sdf.parse(time_b);
			long diff = d1.getTime() - d2.getTime();
			long days = diff / (1000 * 60 * 60 * 24);
			return diff;
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return 0;
		}
	}
	public static String getCurrentTimeZone()
	{  
	    TimeZone tz = TimeZone.getDefault();  
	    return createGmtOffsetString(true,true,tz.getRawOffset());  
	}  
	public static String createGmtOffsetString(boolean includeGmt,  
	                                           boolean includeMinuteSeparator, int offsetMillis) {  
	    int offsetMinutes = offsetMillis / 60000;  
	    char sign = '+';  
	    if (offsetMinutes < 0) {  
	        sign = '-';  
	        offsetMinutes = -offsetMinutes;  
	    }  
	    StringBuilder builder = new StringBuilder(9);  
	    if (includeGmt) {  
	        builder.append("GMT");  
	    }  
	    builder.append(sign);  
	    appendNumber(builder, 2, offsetMinutes / 60);  
	    if (includeMinuteSeparator) {  
	        builder.append(':');  
	    }  
	    appendNumber(builder, 2, offsetMinutes % 60);  
	    return builder.toString();  
	}  
	  
	private static void appendNumber(StringBuilder builder, int count, int value) {  
	    String string = Integer.toString(value);  
	    for (int i = 0; i < count - string.length(); i++) {  
	        builder.append('0');  
	    }  
	    builder.append(string);  
	}  
}
