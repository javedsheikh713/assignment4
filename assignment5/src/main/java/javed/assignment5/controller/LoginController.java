package javed.assignment5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javed.assignment5.model.User;
import javed.assignment5.request.LoginRequest;
import javed.assignment5.request.UserObject;
import javed.assignment5.request.UserRequest;
import javed.assignment5.response.LoginObject;
import javed.assignment5.response.Response;
import javed.assignment5.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	
	

	//@RequestMapping(method = { RequestMethod.POST }, path = "/login")

	//@ResponseBody

	public Response authenticateUser(@RequestBody UserRequest login) {

		 String captcha =null;
		// request.getSession().getAttribute("captcha_security").toString();
		System.out.println("capthcaasa  j;l ===== ");
		//LoginResponse response = userService.validateUser(login);
		
		/*
		 * if (userService.validateCaptcha(captcha, login.getCaptcha())) {
		 * System.out.println("capthcaasa 222  j;l ===== "); User user =
		 * userService.validateUser(login);
		 * 
		 * 
		 * }
		 */

		System.out.println("Finish Method authenticateUser ");
		return null;
		
	}

	@RequestMapping(method = { RequestMethod.POST }, path = "/fetch")

	@ResponseBody
	public Response fetchUser(@RequestBody UserRequest userName) {
		
		return userService.fetchUser(userName.getUserName());
		
	}
	
		
	@GetMapping("/login")
	public String showLoginPage(Model model) {
		UserObject login=new UserObject();
		model.addAttribute("user", login);
		return "login";
	}
	@GetMapping("/update")
	   public String update(@RequestParam String userName,Model model) {
		Response response=userService.fetchUser(userName);
		model.addAttribute("user", response);
		return "update";
	   }
	
	@PostMapping("/login")
	public String login(UserObject loginDto, BindingResult result, Model model,HttpServletRequest request) {

		String captcha = request.getSession().getAttribute("captcha_security").toString();
		
		Response response= userService.validateUser(loginDto,captcha);
		
		if(response.getStatus()=="ERROR") {
			model.addAttribute("user",response);
			return "login";
		}

		model.addAttribute("user", response);
		return "dashboard";
		
	}
	
	@PostMapping("/update")
	public String accountUpdate(UserObject loginDto, BindingResult result, Model model,HttpServletRequest request) {

		String captcha = request.getSession().getAttribute("captcha_security").toString();
		
		Response response= userService.updateAccount(loginDto,captcha);
		
		if(response.getStatus()=="ERROR") {
			model.addAttribute("user",response);
			return "update";
		}

		model.addAttribute("user", response);
		return "dashboard";
		
	}
	
	@GetMapping("/logout")
	public String logout(Model model) {
		UserObject login=new UserObject();
		model.addAttribute("user", login);
		return "login";
	}
}
