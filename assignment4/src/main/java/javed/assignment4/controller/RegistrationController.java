package javed.assignment4.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javed.assignment4.model.Login;
import javed.assignment4.model.User;
import javed.assignment4.service.UserService;

@Controller
public class RegistrationController {
	
	@Autowired
	public UserService userService;
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("registration");
		mav.addObject("user", new User());
		return mav;
	}

	@RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
	public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("user") User user) {
		String responseString = userService.register(user,request);
		
		if("SUCCESS".equalsIgnoreCase(responseString)) {
			ModelAndView mav = new ModelAndView("login");
		    mav.addObject("login", new Login());
		return mav;
		}else {
			
			ModelAndView mav = new ModelAndView("registration");
			mav.addObject("error", "Invalid Captcha");
			return mav;
		}
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("home");
		
		return mav;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home1(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("home");
		
		return mav;
	}
	
	@RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
	public ModelAndView updateAccount(HttpServletRequest request, HttpServletResponse response ,@ModelAttribute("user") User user) {
		
		log.info("User Object ---{} ",user.toString());
		System.out.println("User Object ---=="+user.toString());
		String responseString = userService.updateAccount(user,request);
		
		if("SUCCESS".equalsIgnoreCase(responseString)) {
			ModelAndView mav = new ModelAndView("welcome");
		    mav.addObject("firstname", user.getUsername());
		return mav;
		}else {
			
			ModelAndView mav = new ModelAndView("accountUpdate");
			mav.addObject("error", "Invalid Captcha");
			return mav;
		}
	}
	
	@RequestMapping(value = "/accountUpdate", method = RequestMethod.GET)
	public ModelAndView accountUpdate(HttpServletRequest request, HttpServletResponse response ,@RequestParam("username") String username) {
		User user = userService.getUserByName(username);
		ModelAndView mav = new ModelAndView("accountUpdate");
			mav.addObject("user", user);
			return mav;
		}
	}
	
