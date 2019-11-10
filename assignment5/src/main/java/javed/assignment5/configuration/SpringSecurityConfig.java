package javed.assignment5.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ComponentScan({ "javed.assignment5.*" })
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    // Create 2 users for demo
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");

	}
	 
    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .httpBasic()
                .and().csrf().disable().formLogin().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/login/**").permitAll()
                .antMatchers(HttpMethod.POST, "/login").access("hasRole('USER') or hasRole('ADMIN')")
                .antMatchers(HttpMethod.GET, "/update/").access("hasRole('USER')")
                .antMatchers(HttpMethod.POST, "/update/").access("hasRole('USER')")
                .antMatchers(HttpMethod.GET, "/register/**").permitAll()
                .antMatchers(HttpMethod.GET, "/captcha/**").permitAll()
                .antMatchers(HttpMethod.POST, "/register/**").permitAll()
                .anyRequest().authenticated()
                
               ;
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        //ok for demo
        User.UserBuilder users = User.withDefaultPasswordEncoder();

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("password").roles("USER").build());
        manager.createUser(users.username("admin").password("password").roles("USER", "ADMIN").build());
        return manager;
    }*/

}