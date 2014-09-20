package mvc.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.core.annotations.Handler;
import mvc.core.handlers.Handlers;

/**
 * 请求映射处理
 * @author ying.dong
 *
 */
@Handler
public class RequestMappingHandler extends Handlers{
	
	@Override
	public void doHandle(HttpServletRequest request, HttpServletResponse response) throws RuntimeException {
		ApplicationContext context = ApplicationContext.getInstance();
		//requestMapping 与 method的映射关系
		Map<String, Method> mappingMap = context.getRequestMappingMap();
		//method 与 所属controller 的匹配
		Map<Method, Object> matchedConrollerMap = context.getControllerMethodMatchedMap(); 
		//获取请求URI, 根据请求执行方法
		//默认的uri 包含了contextPath ,此处需要去掉contextPath
		String mappingPath = request.getRequestURI().replace(request.getContextPath(), "").trim();
		Method method = mappingMap.get(mappingPath);
		if(method == null) {
			throw new RuntimeException("no mapping found for: " + mappingPath);
		}
		Object controller = matchedConrollerMap.get(method);
		try {
			method.invoke(controller, new Object[]{request, response});
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

}
