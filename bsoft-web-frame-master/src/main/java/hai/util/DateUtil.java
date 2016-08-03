package hai.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Loanin
 * @date 2014年7月11日
 * @describe 时间处理工具类
 */
public class DateUtil {

	// private static SimpleDateFormat dateFormatT6 = new
	// SimpleDateFormat("HH:mm:ss");
	// private static SimpleDateFormat dateFormatD8 = new
	// SimpleDateFormat("yyyy-MM-dd");
	// private static SimpleDateFormat dateFormatDT15 = new
	// SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	// private static SimpleDateFormat dateFormatDT10 = new
	// SimpleDateFormat("MM-dd HH:mm:ss");
	public static String dateFormat(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}
	
	public static String dateFormatDay(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	public static String dateFormatDT15(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return dateFormat.format(date);
	}

	// public static void main(String[] args) throws Exception {
	// System.out.println(dateFormat(new Date()));
	// System.out.println(stringToDate("20140203T120302"));
	// }

	// 这个函数负责建 各种不规范的时间字符串转换为java的时间类型
	public synchronized static Date stringToDate(String value) throws Exception {// 将

		String df = "";
		if (value.length() == 15) {
			df = "yyyyMMdd'T'HHmmss";
		} else if (value.length() == 8) {
			df = "yyyyMMdd";
		} else {
			boolean isHasT = false;
			// 时间字符串转为时间
			// String valueString = "2014-2-3 12:3:2";
			String[] di = null;
			if (value.contains("T")) {// 有T的用T截断
				di = value.split("T");
				isHasT = true;
			} else
				di = value.split(" ");

			if (di.length >= 1) {
				int c = di[0].split("-").length;
				if (c >= 1)
					df += "yyyy";
				if (c >= 2)
					df += "-MM";
				if (c >= 3)
					df += "-dd";
			}
			if (di.length >= 2) {
				if (isHasT)
					df += "'T'";
				int c = di[1].split(":").length;
				if (c >= 1)
					df += "HH";
				if (c >= 2)
					df += ":mm";
				if (c >= 3)
					df += ":ss";
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat(df);
		return sdf.parse(value);
	}

	public static Date addDateToYear(Date date, int year) {
		Date da = (Date) date.clone();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(da);
		calendar.add(calendar.YEAR, year);
		da = calendar.getTime();
		return da;
	}

	public static Date addDateToMonth(Date date, int month) {
		Date da = (Date) date.clone();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(da);
		calendar.add(calendar.MONTH, month);
		da = calendar.getTime();
		return da;
	}

	public static Date addDateToDay(Date date, int day) {
		Date da = (Date) date.clone();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(da);
		calendar.add(calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
		da = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		return da;
	}

	public static Date addDateToHour(Date date, int hour) {
		Date da = (Date) date.clone();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(da);
		calendar.add(calendar.HOUR, hour);
		da = calendar.getTime();
		return da;
	}
}
