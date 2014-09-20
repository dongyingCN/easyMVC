package mvc.ext.utils;

/**
 * StringUtils,  String工具类
 * @author ying.dong
 *
 */
public class StringUtils {

	/**
	 * 判断str非空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if (str == null || str.trim().equals("")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断是否为null
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		if(str == null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 添加字符窜到起始位置
	 * @param source 源字符串
	 * @param appendStr 要追加的字符串
	 * @return
	 */
	public static String addToIndex(String source, String appendStr) {
		if(isNull(source)) {
			throw new NullPointerException();
		}
		return appendStr + source;
	}
	
	/**
	 * 格式化path (example: 格式化前, "super/sub//test", 格式化后， /super/sub/test)
	 * @param path
	 * @return
	 */
	public static String formatPathString(String path) {
		if(isNull(path)) {
			throw new NullPointerException();
		}
		path = path.trim();
		if(!path.startsWith("/")) {
			path = addToIndex(path, "/");
		}
		return path.replace("//", "/");
	}
	
}
