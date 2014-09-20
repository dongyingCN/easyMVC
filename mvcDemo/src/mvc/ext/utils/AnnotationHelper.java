package mvc.ext.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 注解筛选
 * @author ying.dong
 *
 */
public class AnnotationHelper {

	/**
	 * 扫描指定annotation修饰的class
	 * @param annotation
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static Map<Class<?>, Annotation> filtrateClassAnnotation(Class<?> annotation) throws ClassNotFoundException {
		//扫描出所有拥有注解修饰class的class
		Map<Class<?>, Annotation[]> annotationTypeMap = AnnotationScanner.scanForType();
		//遍历筛选出指定annotation修饰的class, 并单独存入容器
		return  (Map<Class<?>, Annotation>)getAnnotationMap(annotationTypeMap, annotation);
	}
	
	/**
	 * 扫描指定annotation修饰的方法
	 * @param annotation
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static Map<Method, Annotation> filtrateMethodAnnotation(Class<?> annotation) throws ClassNotFoundException {
		Map<Method, Annotation[]> annotationMethodMap = AnnotationScanner.scanForMethod();
		return (Map<Method, Annotation>) getAnnotationMap(annotationMethodMap, annotation);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map<?, ?> getAnnotationMap(Map<?, ?> resultMap, Class<?> annotation) {
		Map containerMap = new HashMap();
		for(Entry<?, ?> entry: resultMap.entrySet()) {
			Annotation[] annotations = (Annotation[]) entry.getValue();
			for(int i = 0; i < annotations.length; i++) {
				if(annotations[i].annotationType().equals(annotation)) {
					containerMap.put(entry.getKey(), annotations[i]);
				}
			}
		}
		return containerMap;
	}
	
}
