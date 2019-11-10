package javed.assignment5.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javed.assignment5.common.JsonUtils;
import javed.assignment5.common.JwtToken;
import javed.assignment5.request.UserRequest;
import javed.assignment5.response.Response;

//@Component
public class MyFilter { //implements Filter {

	
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse) response; 
		
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Methods",	"Cache-Control, Pragma, Origin, Authorization, Content-Type, X-Requested-With");
		resp.addHeader("Access-Control-Allow-Headers", "GET, PUT, OPTIONS, X-XSRF-TOKEN");
		System.out.println(req.getRequestURI());
		
		System.out.println("===============   "+req.getSession().getAttribute("captcha_security"));

		try {
			/*
			 * String token=req.getHeader("Authorization"); UserRequest
			 * userRequest=jwtToken.parseToken(token);
			 * System.out.println(req.getRequestURI());
			 * if(req.getRequestURI().equalsIgnoreCase("/login") ||
			 * req.getRequestURI().equalsIgnoreCase("/register")) {
			 * 
			 * }else { if(!jwtToken.validateToken(token)) { throw new
			 * Exception("Invalid-Token"); } }
			 */
		chain.doFilter(request, response);
		}catch(Exception e) {
			e.printStackTrace();
			resp.resetBuffer();
			resp.setHeader("Content-Type", "application/json");
			Response custResp=new Response();
			custResp.setStatus("ERROR");
			custResp.setError(new javed.assignment5.response.Error("SYS_003","Invalid Request"));
			String jsonResponse=JsonUtils.convertToJson(custResp);
			resp.getOutputStream().write(jsonResponse.getBytes());
			resp.flushBuffer();
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
