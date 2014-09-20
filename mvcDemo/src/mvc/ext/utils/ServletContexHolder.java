package mvc.ext.utils;

import javax.servlet.ServletContext;

/**
 * 持有servletContex, 此servletContex 由DispatcherFilter在init时注入
 * @author ying.dong
 *
 */
public class ServletContexHolder {

	private static ServletContexHolder contextHolder = null;
	//serveletContext
	private ServletContext servletContex;
	
	private ServletContexHolder() {}
	
	public synchronized static ServletContexHolder getInstance() {
		if(contextHolder == null) {
			contextHolder = new ServletContexHolder();
		}
		return contextHolder;
	}

	public ServletContext getServletContex() {
		return servletContex;
	}

	public void setServletContex(ServletContext servletContex) {
		this.servletContex = servletContex;
	}
	
}
