package mvc.core.annotations.resolver;


/**
 * 定义annotation 处理器的模板， 具体由其子类实现
 * @author ying.dong
 *
 */
public abstract class AnnotationResolverTemplate {

	/**
	 * 模板方法
	 * @throws Exception 
	 * @throws LogicException 
	 */
	public void resolve() throws Exception {
		doProcess();
	}
	
	/**
	 * 钩子方法
	 * 由子类自定义实现
	 * @throws LogicException 
	 */
	public void doProcess() throws Exception {
		
	}
	
}
