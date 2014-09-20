package mvc.core.handlers;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 创建handler对象， 并设定nextHandler
 * @author ying.dong
 *
 */
public class HandlerFactory {

	//handler 计数
	private static int countHandlers = 0;
	
	private HandlerFactory() {}
	
	/**
	 * 实例化Handler
	 * @param handlersClassMap
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Handlers getHandler(Map<Class<?>, Annotation> handlersClassMap) throws InstantiationException, IllegalAccessException {
		//第一个
		Handlers firstHandler = null;
		//上一个
		Handlers lastHandler = null;
		for(Entry<Class<?>, Annotation> entry: handlersClassMap.entrySet()) {
			Handlers tempHandler = (Handlers) entry.getKey().newInstance();
			//第一次循环的handler 设置为第一个
			if(countHandlers == 0) {
				firstHandler = tempHandler;
				lastHandler = tempHandler;
			} else {
				//设置下一个handler
				lastHandler.nextHandler = tempHandler;
				lastHandler = tempHandler;
			}
			countHandlers++;
		}
		countHandlers = 0;
		return firstHandler;
	}
	
}
