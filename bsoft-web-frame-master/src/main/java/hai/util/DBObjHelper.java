package hai.util;

import java.util.Date;

public class DBObjHelper {
	
	public static float dbObjToFloat(Object object) {
		String value = dbObjToString(object);
		if (value != null && value != "")
			return Float.valueOf(value);
		return 0;
	}

	public static int dbObjToInt(Object object) {
		String value = dbObjToString(object);
		if (value != null && value != "")
			return Integer.valueOf(value);
		return 0;
	}

	public static String dbObjToString(Object object) {
		if (object != null)
			return object.toString();
		return "";
	}

	public static Date dbObjToDate(Object object) {
		if (object != null)
			return (Date) object;
		return null;
	}

	public static boolean dbObjToBool(Object object) {
		return dbObjToString(object).equals("1");
	}
}
