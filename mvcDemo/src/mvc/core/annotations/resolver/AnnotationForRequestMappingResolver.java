package mvc.core.annotations.resolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import mvc.core.ApplicationContext;
import mvc.core.annotations.AnnotationResolver;
import mvc.core.annotations.Controller;
import mvc.core.annotations.RequestMapping;
import mvc.ext.utils.AnnotationHelper;
import mvc.ext.utils.StringUtils;

/**
 * requestMapping 映射处理
 * @author ying.dong
 *
 */
@AnnotationResolver
public class AnnotationForRequestMappingResolver extends AnnotationResolverTemplate{

	@Override
	public void doProcess() throws ClassNotFoundException {
		Map<String, String>	annotationControllerMappingMap = filtrateRequestMappingClass();
		Map<String, Method>	mappingNameMap = filtrateRequestMappingMethod(annotationControllerMappingMap);
		//获取method与所属controller的配对
		Map<Method, Object> controllerMethodMatchedMap = matchControllerWithMethod(mappingNameMap);
		
		//转交ApplicationContext 持有
		ApplicationContext context = ApplicationContext.getInstance();
		context.setRequestMappingMap(mappingNameMap);
		context.setControllerMethodMatchedMap(controllerMethodMatchedMap);
	}
	
	/**
	 * 扫描RequestMapping 修饰的class
	 * @return 
	 * @throws ClassNotFoundException
	 */
	public Map<String, String> filtrateRequestMappingClass() throws ClassNotFoundException {
		Map<String, String> annotationControllerMappingMap = new HashMap<String, String>();
		//扫描出RequestMapping 修饰的class
		Map<Class<?>, Annotation> annotationFiltrateClassMap = AnnotationHelper.filtrateClassAnnotation(RequestMapping.class);
		for(Entry<Class<?>, Annotation> entry: annotationFiltrateClassMap.entrySet()) {
			Class<?> clazz = entry.getKey();
			//拥有RequestMapping修饰， 但没有@Controller修饰
			if(!clazz.isAnnotationPresent(Controller.class)) {
				throw new RuntimeException("Class " + clazz.getName() + " must be modified by @Controller !");
			}
			RequestMapping requestMapping = (RequestMapping) entry.getValue();
			//获取controllerName
			String controllerName = AnnotationForControllerResolver.getDefaultControllerName(clazz.getAnnotation(Controller.class).value(), clazz.getSimpleName());
			//Map<controllerName, RequestMapping Value>
			annotationControllerMappingMap.put(controllerName, requestMapping.value());
		}
		return annotationControllerMappingMap;
	}
	
	/**
	 * 扫描组装requestMapping的映射名，使其与 method 关联
	 * @param annotationControllerMappingMap
	 * @return
	 * @throws ClassNotFoundException
	 */
	public Map<String, Method> filtrateRequestMappingMethod(Map<String, String> annotationControllerMappingMap) throws ClassNotFoundException {
		Map<Method, Annotation> map = AnnotationHelper.filtrateMethodAnnotation(RequestMapping.class);
		Map<String, Method> mappingNameMap = new HashMap<String, Method>();
		for(Entry<Method, Annotation> entry: map.entrySet()) {
			//method所属类
			Class<?> methodClass = entry.getKey().getDeclaringClass();
			//method 所属类不是Controller
			if(!methodClass.isAnnotationPresent(Controller.class)) {
				throw new RuntimeException("Method " + entry.getKey().getName() + " must be modified by @Controller !");
			}
			//method 所在类的controller标签的值
			String methodControllerValue = methodClass.getAnnotation(Controller.class).value();
			//method所属Controller 的name
			String methodControllerName = AnnotationForControllerResolver.getDefaultControllerName(methodControllerValue, methodClass.getSimpleName());
			//判断method 所属的 Controller
			if(annotationControllerMappingMap.containsKey(methodControllerName)) {
				RequestMapping requestMapping = (RequestMapping) entry.getValue();
				//获取mapping name
				String mappingName = getRequestMappingName(methodControllerName, annotationControllerMappingMap.get(methodControllerName), entry.getKey().getName(), requestMapping.value());
				mappingNameMap.put(mappingName, entry.getKey());
			}
		}
		return mappingNameMap;
	}
	
	/**
	 * method invoke时，需要传入所属的对象，故在此先得出
	 * @param methodMap
	 * @return
	 */
	public Map<Method, Object> matchControllerWithMethod(Map<String, Method> methodMap) {
		Map<Method, Object> matchedMap = new HashMap<Method, Object>();
		Map<String, Object> controllerInstanceMap = ApplicationContext.getInstance().getControllerInstanceMap();
		for(Entry<String, Method> entry: methodMap.entrySet()) {
			Method method = entry.getValue();
			String className = method.getDeclaringClass().getName();
			if(controllerInstanceMap.containsKey(className)) {
				matchedMap.put(method, controllerInstanceMap.get(className));
			}
		}
		return matchedMap;
	}
	
	
	/**
	 * 根据requestMapping修饰的值获取 mapping 映射名
	 * @param controllerName
	 * @param controllerRequestMapping
	 * @param methodName
	 * @param methodRequestMapping
	 * @return
	 */
	public String getRequestMappingName(String controllerName, String controllerRequestMapping, String methodName, String methodRequestMapping) {
		String controllerPath = StringUtils.formatPathString(controllerRequestMapping);
		String methodPath = StringUtils.formatPathString(methodRequestMapping);
		//方法映射为空时，默认为 controllerName + methodName
		if(!StringUtils.isNotEmpty(methodRequestMapping)) {
			controllerPath = StringUtils.formatPathString(controllerName);
			methodPath = StringUtils.formatPathString(methodName);
		}
		return StringUtils.formatPathString(controllerPath + methodPath);
	}
	
}
