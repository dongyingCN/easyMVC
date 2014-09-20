package mvc.ext.utils;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 扫描所有class文件，并将路径转换为packgeName + className 的形式，以便用于反射
 * @author ying.dong
 *
 */
public class ClassFileScanner {

	//windows :C:\Users\Administrator\Desktop\mvcDemo\build\classes
	//linux or mac:  \Users\Administrator\Desktop\mvcDemo\build\classes\
	private static final String APP_CLASS_PATH = new File(Thread.currentThread().getContextClassLoader().getResource("").getPath()).getAbsolutePath();
	private static final String CLASS_SUFFIX = ".class";
	
	public static void main(String[] args) {
		Set<String> set = scanClassFiles();
		for(String fileName: set) {
			System.out.println(fileName);
		}
	}
	
	/**
	 * 递归扫描所有class文件
	 * @param file
	 * @return
	 */
	private static Collection<String> getAllClassFile(File file, Collection<String> collection) {
		if(file.isFile() && file.getName().endsWith(CLASS_SUFFIX)) {
			collection.add(parseClassFileToFullName(file));
		} else {
			File[] files = file.listFiles();
			for(File tempFile: files) {
				getAllClassFile(tempFile, collection);
			}
		}
		return collection;
	}
	
	/**
	 * 将class的路径名转换为package
	 * @param file
	 * @return
	 */
	private static String parseClassFileToFullName(File file) {
		StringBuilder fileNameSb = new StringBuilder(file.getPath());
		if (fileNameSb.toString().startsWith(APP_CLASS_PATH)) {
			fileNameSb.delete(0, APP_CLASS_PATH.length());
			//过滤class文件并转换文件名为packageName + className
			if(fileNameSb.toString().endsWith(CLASS_SUFFIX)) {
				fileNameSb.delete(fileNameSb.length() - CLASS_SUFFIX.length(), fileNameSb.length());
			}
		}
		String fileName = fileNameSb.toString().replace(File.separator, ".");
		if (fileName.startsWith(".")) {
			fileName = fileName.substring(1, fileName.length());
		}
		return fileName;
	}
	
	/**
	 * 扫描所有class文件，并将路径转换为packgeName + className 的形式
	 * @return
	 */
	public static Set<String> scanClassFiles() {
		URL dir = Thread.currentThread().getContextClassLoader().getResource("");
		Set<String> fileNameSet = new HashSet<>();
		if(dir.getProtocol().equals("file")) {
			File pathFile = new File(APP_CLASS_PATH);
			getAllClassFile(pathFile, fileNameSet);
		}
		return fileNameSet;
	}
	
}
