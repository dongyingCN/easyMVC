package mvc.ext.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 扫描注解
 * @author ying.dong
 *
 */
public class AnnotationScanner {

	/**
	 * 根据扫描出的class文件加载
	 */
	private static Set<Class<?>> loadedClassSet = new HashSet<>();

	/**
	 * 加载class文件
	 * @throws ClassNotFoundException
	 */
	private static void loadClassFiles() throws ClassNotFoundException {
		if(loadedClassSet.isEmpty()) {
			Set<String> set = ClassFileScanner.scanClassFiles();
			for(String className: set) {
//				Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
				Class<?> clazz = Class.forName(className);
				loadedClassSet.add(clazz);
			}
		}
	}
	
	/**
	 * 扫描修饰class的注解， 结果保存在Map<class, annotations[]>
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Map<Class<?>, Annotation[]> scanForType() throws ClassNotFoundException {
		loadClassFiles();
		Map<Class<?>, Annotation[]> annotationTypeMap = new HashMap<Class<?>, Annotation[]>();
		for(Class<?> clazz: loadedClassSet) {
			Annotation[] annotations = clazz.getAnnotations();
			if(annotations.length >= 0) {
				annotationTypeMap.put(clazz, annotations);
			}
		}
		return annotationTypeMap;
	}
	
	
	/**
	 * 扫描修饰 method 的注解， 结果保存在Map<method, annotations[]>
	 * @return annotationMethodMap
	 * @throws ClassNotFoundException
	 */
	public static Map<Method, Annotation[]> scanForMethod() throws ClassNotFoundException {
		loadClassFiles();
		Map<Method, Annotation[]> annotationMethodMap = new HashMap<Method, Annotation[]>();
		for(Class<?> clazz: loadedClassSet) {
			Method[] methods = clazz.getMethods();
			for(int i = 0; i < methods.length; i++) {
				Annotation[] annotations = methods[i].getAnnotations();
				if(annotations.length >= 0) {
					annotationMethodMap.put(methods[i], annotations);
				}
			}
		}
		return annotationMethodMap;
	}
	
	
	public static Annotation[] scanForParams() {
		return null;
	}
	
}
