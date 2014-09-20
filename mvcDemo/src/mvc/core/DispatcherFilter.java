package mvc.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.core.annotations.resolver.AnnotationForControllerResolver;
import mvc.core.handlers.Handlers;
import mvc.ext.utils.ServletContexHolder;

/**
 * 
 * @author ying.dong
 *
 */
public class DispatcherFilter implements Filter {

	private Handlers handler;
	

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		//开始执行handler
		handler.process(httpRequest, httpResponse);
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		//向Holder注入ServletContext 对象
		ServletContexHolder.getInstance().setServletContex(fConfig.getServletContext());
		//初始化annotation
		try {
			new AnnotationInitResolver().initAnnotationResolver(AnnotationForControllerResolver.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取handler
		handler = ApplicationContext.getInstance().getHandler();
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
