package mvc.core;

import java.lang.reflect.Method;
import java.util.Map;

import mvc.core.handlers.Handlers;

/**
 * 
 * @author ying.dong
 *
 */
public class ApplicationContext {

	private static ApplicationContext applicationContext;
	//对handler的持有
	private Handlers handler;
	//对映射的持有
	private Map<String, Method> requestMappingMap;
	//保存method对应的Object
	private Map<Method, Object> controllerMethodMatchedMap;
	//保存controller的实例
	private Map<String, Object> controllerInstanceMap;
	
	private ApplicationContext(){}
	
	public synchronized static ApplicationContext getInstance() {
		if(applicationContext == null) {
			applicationContext = new ApplicationContext();
		}
		return applicationContext;
	}
	
	public Handlers getHandler() {
		return handler;
	}

	public void setHandler(Handlers handler) {
		this.handler = handler;
	}

	public Map<String, Method> getRequestMappingMap() {
		return requestMappingMap;
	}

	public void setRequestMappingMap(Map<String, Method> requestMappingMap) {
		this.requestMappingMap = requestMappingMap;
	}

	public Map<Method, Object> getControllerMethodMatchedMap() {
		return controllerMethodMatchedMap;
	}

	public void setControllerMethodMatchedMap(
			Map<Method, Object> controllerMethodMatchedMap) {
		this.controllerMethodMatchedMap = controllerMethodMatchedMap;
	}

	public Map<String, Object> getControllerInstanceMap() {
		return controllerInstanceMap;
	}

	public void setControllerInstanceMap(Map<String, Object> controllerInstanceMap) {
		this.controllerInstanceMap = controllerInstanceMap;
	}
	
}
