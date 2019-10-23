package javed.assignment4.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javed.assignment4.filter.JwtTokenFilter;

@Configuration
@EnableWebSecurity
@ComponentScan({ "javed.assignment4.*" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    JwtToken jwtToken;   
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	    			
		  http .httpBasic().disable() .csrf().disable()
		  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		  .and() .authorizeRequests() .antMatchers("/login").permitAll()
		  .antMatchers(HttpMethod.GET, "/register/**").permitAll()
		  .antMatchers(HttpMethod.GET, "/register/**").permitAll()
		  .antMatchers(HttpMethod.GET, "/home/**").permitAll()
		  .antMatchers(HttpMethod.GET, "/register/**").permitAll()
		  .antMatchers(HttpMethod.GET, "/captcha/**").permitAll()
		  .antMatchers(HttpMethod.POST, "/registerProcess/**").permitAll()
		  .antMatchers(HttpMethod.POST, "/loginProcess/**").permitAll()
		  .antMatchers(HttpMethod.GET, "/accountUpdate/**").access("hasRole('USER') or hasRole('ADMIN')")
		  .antMatchers(HttpMethod.POST, "/updateAccount/**").access("hasRole('USER') or hasRole('ADMIN')")
		  .anyRequest().authenticated() .and() .apply(new JwtConfigurer(jwtToken));
		  
		  http.addFilterBefore(new JwtTokenFilter(jwtToken), BasicAuthenticationFilter.class);
		 
        //@formatter:on
    }
    
 
	/*
	 * public void addResourceHandlers(ResourceHandlerRegistry registry) {
	 * registry.addResourceHandler("/resources/**").addResourceLocations(
	 * "/resources/");
	 * 
	 * }
	 * 
	 * @Bean public InternalResourceViewResolver viewResolver() {
	 * InternalResourceViewResolver viewResolver = new
	 * InternalResourceViewResolver(); viewResolver.setViewClass(JstlView.class);
	 * viewResolver.setPrefix("/WEB-INF/pages/"); viewResolver.setSuffix(".jsp");
	 * return viewResolver;
	 * 
	 * }
	 */
		 
}