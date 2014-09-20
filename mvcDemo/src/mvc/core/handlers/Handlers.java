package mvc.core.handlers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * handler
 * @author ying.dong
 *
 */
public abstract class Handlers {

	protected Handlers nextHandler;

	public abstract void doHandle(HttpServletRequest request, HttpServletResponse response) throws RuntimeException;
	
	/**
	 * 交给下一个handler处理
	 * @param request
	 * @param response
	 */
	protected void next(HttpServletRequest request, HttpServletResponse response) {
		if(nextHandler != null) {
			nextHandler.doHandle(request, response);
		}
	}
	
	/**
	 * 模板方法
	 * @param request
	 * @param response
	 */
	public void process(HttpServletRequest request, HttpServletResponse response) {
		//自己处理
		doHandle(request, response);
		//自己处理完交给下个handler继续处理
		next(request, response);
	}
	
}
