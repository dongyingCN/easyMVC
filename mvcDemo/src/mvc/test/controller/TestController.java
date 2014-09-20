package mvc.test.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.core.annotations.Controller;
import mvc.core.annotations.RequestMapping;

@Controller
@RequestMapping
public class TestController {
	
	@RequestMapping("/test")
	public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		try {
//			response.sendError(407, "Need authentication!!!" );
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		response.getWriter().write("hhahahahahaha");
		System.out.println("----------test方法执行---------------");
	}
	
	@RequestMapping
	public void test2(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("--------------test2 方法执行-----------");
	}
	
}
