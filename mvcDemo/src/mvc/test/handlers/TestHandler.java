package mvc.test.handlers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.core.annotations.Handler;
import mvc.core.handlers.Handlers;

@Handler
public class TestHandler extends Handlers{

	@Override
	public void doHandle(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("----------------自定义handler执行——————");
	}


}
