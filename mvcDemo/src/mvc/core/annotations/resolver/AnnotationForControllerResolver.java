package mvc.core.annotations.resolver;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import mvc.core.ApplicationContext;
import mvc.core.annotations.AnnotationResolver;
import mvc.core.annotations.Controller;
import mvc.ext.utils.AnnotationHelper;
import mvc.ext.utils.StringUtils;
/**
 * controller annotation处理
 * @author ying.dong
 *
 */
@AnnotationResolver
public class AnnotationForControllerResolver extends AnnotationResolverTemplate{

	/**
	 * 筛选出包含controller修饰的class
	 * @return
	 * @throws  Exception
	 */
	@Override
	public void doProcess() throws Exception {
		//将Controller实例转交ApplicationContext持有
		ApplicationContext.getInstance().setControllerInstanceMap(getControllerInstance());
	}
	
	/**
	 * 筛选出包含controller修饰的class (Map<controllerName, Class<?>>)
	 * @return
	 * @throws ClassNotFoundException
	 */
	public Map<String, Class<?>> filtrateControllerClass() throws ClassNotFoundException {
		Map<String, Class<?>> annotationControllerMap = new HashMap<String, Class<?>>();
		//遍历筛选出controller修饰的class, 并单独存入容器
		Map<Class<?>, Annotation> annotationFiltrateClassMap = AnnotationHelper.filtrateClassAnnotation(Controller.class);
		for(Entry<Class<?>, Annotation> entry: annotationFiltrateClassMap.entrySet()) {
			Class<?> tempClass = entry.getKey();
			Controller controller = (Controller)entry.getValue();
			String controllerName = getDefaultControllerName(controller.value(), tempClass.getSimpleName());
			//已包含相同的Controller名
			if(annotationControllerMap.containsKey(controllerName)) {
				throw new RuntimeException("controller " + controllerName + " has already exists, please check it again!");
			}
			annotationControllerMap.put(controllerName, tempClass);
		}
		return annotationControllerMap;
	}
	
	/**
	 * 给controller tag 修饰的class 设置controller名, 如果在标签中指定， 那么直接使用标签指定的名字， 否则使用className (首字母小写)
	 * @param controllerTagValue
	 * @param className
	 * @return
	 */
	public static String getDefaultControllerName(String controllerTagValue, String className) {
		String controllerName = controllerTagValue;
		//controller tag 默认value是""
		if(StringUtils.isNotEmpty(controllerTagValue)) {
			//如果没设置controller的value, 那么给出默认 controllerName(其首字母小写), 否则直接使用设置的value
			//首字母转换为小写
			String firstChar = className.substring(0,1).toLowerCase();
			controllerName = firstChar + className.substring(1, className.length());
		}
		return controllerName;
	}
	
	/**
	 * 实例化Controller
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public Map<String, Object> getControllerInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Map<String, Class<?>> classMap = filtrateControllerClass();
		Map<String, Object> controllerInstanceMap = new HashMap<String, Object>();
		for(Entry<String, Class<?>> entry: classMap.entrySet()) {
			Class<?> clazz = entry.getValue();
			Object controller = clazz.newInstance();
			controllerInstanceMap.put(clazz.getName(), controller);
		}
		return controllerInstanceMap;
	}
	
	
}
