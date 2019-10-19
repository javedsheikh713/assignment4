package javed.assignment4.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javed.assignment4.service.UserService;

@Controller
public class CaptchaController {

	@Autowired
	public UserService userService;
	
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void fetchCaptcha(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("captcha image");
		userService.fetchCaptcha(request,response);
		
	}
}
