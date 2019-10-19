package javed.assignment4.filter;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javed.assignment4.config.JwtToken;
import javed.assignment4.model.User;

@Component
public class JwtTokenFilter extends GenericFilterBean {   
	
	@Autowired
	private JwtToken jwtToken;   
	
	@Autowired
	public JwtTokenFilter(JwtToken jwtToken) {
    this.jwtToken = jwtToken;
}   
	
	 
	@Override
public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
    throws IOException, ServletException{    
		
		System.out.println("===================  inside filter--------------------");
		
		 User u=new User();
		  u.setUsername("shiza");
		  u.setRole("ROLE_USER");
		
		 String token=this.jwtToken.generateToken(u);
		  
		HttpServletRequest hreq=(HttpServletRequest) req;
		
		
	
		// String token = jwtToken.resolveToken(hreq);
		 
		  if (token != null && jwtToken.validateToken(token)) {
			  
			  System.out.println("===================  valid token--------------------");
		  
		  Authentication auth = token != null ?  jwtToken.getAuthentication(token) : null;
		  System.out.println("===================  valid token2 --------------------");
		  
		  SecurityContextHolder.getContext().setAuthentication(auth);
		  
		  }else {
			  System.out.println("===================  invalid token--------------------");
		  }
		 
    
    filterChain.doFilter(req, res);
}

	public JwtToken getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(JwtToken jwtToken) {
		this.jwtToken = jwtToken;
	}
	

}