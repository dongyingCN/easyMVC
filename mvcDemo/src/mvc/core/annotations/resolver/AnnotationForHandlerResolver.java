package mvc.core.annotations.resolver;

import java.lang.annotation.Annotation;
import java.util.Map;

import mvc.core.ApplicationContext;
import mvc.core.annotations.AnnotationResolver;
import mvc.core.annotations.Handler;
import mvc.core.handlers.HandlerFactory;
import mvc.core.handlers.Handlers;
import mvc.ext.utils.AnnotationHelper;

/**
 * handler annotation处理
 * @author ying.dong
 *
 */
@AnnotationResolver
public class AnnotationForHandlerResolver extends AnnotationResolverTemplate{

	@Override
	public void doProcess() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Map<Class<?>, Annotation> handlerMap = AnnotationHelper.filtrateClassAnnotation(Handler.class);
		Handlers handler = HandlerFactory.getHandler(handlerMap);
		//ApplicationContext 持有handler
		ApplicationContext.getInstance().setHandler(handler);
	}
	
}
