package javed.assignment4.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javed.assignment4.config.JwtToken;
import javed.assignment4.model.Login;
import javed.assignment4.model.User;
import javed.assignment4.service.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;
	
	@Autowired
	JwtToken jwtToken;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	  public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView mav = new ModelAndView("login");
	    mav.addObject("login", new Login());
	    return mav;
	  }
	
	
	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	  public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response,
	  @ModelAttribute("login") Login login) {
	    ModelAndView mav = null;
	    String captcha = request.getSession().getAttribute("captcha_security").toString();
	    System.out.println("capthcaasa  j;l ===== ");
	    if (userService.validateCaptcha(captcha,login.getCaptcha())) {
	    	 System.out.println("capthcaasa 222  j;l ===== ");
	    User user = userService.validateUser(login);
	    if (Objects.nonNull(user)) {
	    mav = new ModelAndView("welcome");
	    mav.addObject("user", user);
	    response.setHeader("Authorization", jwtToken.generateToken(user));
	    
	    } else {
	    mav = new ModelAndView("login");
	    mav.addObject("message", "Username or Password is wrong!!");
	   
	   	  }
	    
	}else {
		mav = new ModelAndView("login");
		mav.addObject("error", "Captcha Invalid");
	}
  
	   
	    System.out.println("finishhh  j;l ===== ");
	    return mav;
	}
	
	
	
}
