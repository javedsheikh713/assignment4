package javed.assignment5.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import javed.assignment5.model.User;
import javed.assignment5.request.UserObject;
import javed.assignment5.request.UserRequest;
import javed.assignment5.response.LoginObject;
import javed.assignment5.response.Response;
import javed.assignment5.service.UserService;

@Controller
public class RegistrationController {
	
	@Autowired
	public UserService userService;
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);


	@GetMapping("/register")
    public String registerForm(Model model) {
		model.addAttribute("user",new UserObject());
		return "register";
	}
		
	@RequestMapping("/register")
	   public String registerUser(UserObject user,Model model,HttpServletRequest request) {
		
		String captcha = request.getSession().getAttribute("captcha_security").toString();
		Response response=userService.register(user,captcha);
		model.addAttribute("user", response);
		if(response.getStatus().equalsIgnoreCase("SUCCESS")) {
			return "login";
			
		}else {
			return "register";
		}
	      
	   }

	
	
	}
	
