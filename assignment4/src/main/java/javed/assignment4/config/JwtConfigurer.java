package javed.assignment4.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javed.assignment4.filter.JwtTokenFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {  
	
	@Autowired
	private JwtToken jWtToken; 
	
	public JwtConfigurer(JwtToken jwtTokenProvider) {
    this.jWtToken = jwtTokenProvider;
}  
	
	@Override
public void configure(HttpSecurity http) throws Exception {
    JwtTokenFilter customFilter = new JwtTokenFilter(jWtToken);
    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
}
}
