package mvc.core;


import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Map.Entry;

import mvc.core.annotations.AnnotationResolver;
import mvc.core.annotations.resolver.AnnotationResolverTemplate;
import mvc.ext.utils.AnnotationHelper;

/**
 * 初始化一系列处理Annotation的resolver， 包括开发人员自定义的
 * @author ying.dong
 *
 */
public class AnnotationInitResolver {

	/**
	 * 初始化
	 * @param priorResolver 优先执行的resolver
	 * @throws Exception 
	 * @throws LogicException
	 */
	public void initAnnotationResolver(Class<?> priorResolver) throws Exception {
		//先扫描
		 Map<Class<?>, Annotation> map = AnnotationHelper.filtrateClassAnnotation(AnnotationResolver.class);
		
		//如果设置了优先执行的resolver, 那么首先执行设置的
		if(priorResolver != null) {
			map.remove(priorResolver);
			invokeTemplateMethod(priorResolver);
		}
		for(Entry<Class<?>, Annotation> entry: map.entrySet()) {
			Class<?> clazz = entry.getKey();
			invokeTemplateMethod(clazz);
		}
	}
	
	private void invokeTemplateMethod(Class<?> clazz) throws Exception {
		if(clazz.getSuperclass().equals(AnnotationResolverTemplate.class)) {
			//反射执行模板方法
			AnnotationResolverTemplate template = null;
			template = (AnnotationResolverTemplate) clazz.newInstance();
			template.resolve();
		}
	}
	
}
