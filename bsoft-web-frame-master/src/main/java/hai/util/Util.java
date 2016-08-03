package hai.util;

public class Util {

	//替换字符串
	public static String multiReplace(String source, Object[] arr) {
		for (int i = 0; i < arr.length; i++) {
			String regex = "{" + i + "}";
			source = source.replace(regex, arr[i] == null ? null : arr[i].toString());
		}
		return source;
	}
}
