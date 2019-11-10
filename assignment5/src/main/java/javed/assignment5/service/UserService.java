package javed.assignment5.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javed.assignment5.common.JwtToken;
import javed.assignment5.exception.AppException;
import javed.assignment5.model.User;
import javed.assignment5.repository.UserRepository;
import javed.assignment5.request.LoginRequest;
import javed.assignment5.request.UserObject;
import javed.assignment5.request.UserRequest;
import javed.assignment5.response.Error;
import javed.assignment5.response.LoginObject;
import javed.assignment5.response.Response;




@Service
public class UserService {

	
	  @Autowired 
	  private UserRepository userRepository;
	  
	  @Autowired
	  private JwtToken jwtToken;
	 
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	char data[][] = { { 'z', 'e', 't', 'c', 'o', 'd', 'e' }, { 'l', 'i', 'n', 'u', 'x' },
			{ 'f', 'r', 'e', 'e', 'b', 's', 'd' }, { 'u', 'b', 'u', 'n', 't', 'u' }, { 'j', 'e', 'e' } };
	int width = 150;
	int height = 50;

	public UserObject validateUser(UserObject login,String captcha) {
		
		log.info("Start Validate user request ");
		UserObject response=new UserObject();
		try {
		if(!validateCaptcha(captcha, login.getCaptcha())) {
			throw new AppException(new Error("SYS_006","Invalid Captcha"));
		}
		
		    User user=new User();
			user=userRepository.findByUsername(login.getUserName());
			if(user.getPassword().equals(login.getPassword())) {
				log.info("Finish Validate user request ");
			response =prepareUserObject(user);
		//String token=	jwtToken.generateToken(user.getUsername(), user.getRole());
		//response.setToken(token);
			
			}else {
				log.info("Finish Validate user request else condition ");
				response.setError(new javed.assignment5.response.Error("SYS_001", "Invalid User Name Or Password"));
			}
			
	} 
	catch(AppException e) {
		log.error("Error Occured during user registration");
		response=new UserObject();
		response.setStatus("ERROR");
		response.setError(new Error(e.getCode(),e.getMessage()));
	}
catch (Exception e) {
	
	log.error("Error Occured during user registration");
	response=new UserObject();
	response.setStatus("ERROR");
	response.setError(new Error("SYS_002", "System not available this time"));
}
			log.info("Finish Validate user request ");
	
			return response;
	}
	
	
	
	private LoginObject prepareLoginResponse(User user) {
		// TODO Auto-generated method stub
		LoginObject response=new LoginObject();
		response.setEmail(user.getEmail());
		response.setFirstname(user.getFirstname());
		response.setRole(user.getRole());
		response.setUsername(user.getUsername());
		response.setStatus("SUCCESS");
		return response;
	}
	
	private UserObject prepareUserObject(User user) {
		// TODO Auto-generated method stub
		UserObject response=new UserObject();
		response.setEmail(user.getEmail());
		response.setFirstName(user.getFirstname());
		response.setRole(user.getRole());
		response.setUserName(user.getUsername());
		response.setStatus("SUCCESS");
		return response;
	}

	public boolean validateCaptcha(String captcha,String verifyCaptcha) {
		log.info("Start Validate Captcha");
		boolean isValid=false;
		if (captcha.equals(verifyCaptcha)) {
			
			isValid= true;
		} 
		
		log.info("Finish Validate Captcha");
		return isValid;
	}

	public Response register(UserObject request,String captcha) {
		
		log.info("Start register");
		Response response=null;
		try {
		
			if(!validateCaptcha(captcha, request.getCaptcha())) {
				throw new AppException(new Error("SYS_006","Invalid Captcha"));
			}
		//fetch user user name should be unique
		User user=new User();
		if(userRepository.findByUsername(request.getUserName())!=null) {
			throw new AppException(new Error("SYS_004","User Name Already Exist"));
		}
		 
		 user.setFirstname(request.getFirstName());
		 user.setEmail(request.getEmail());
		 user.setPassword(request.getPassword());
		 user.setRole("ROLE_USER");
		 user.setUsername(request.getUserName());
	     userRepository.save(user);
	     response=new UserObject();
			response.setStatus("SUCCESS");
			response.setSuccessMessage("User Register Successfully");
		} 
		catch(AppException e) {
			log.error("Error Occured during user registration");
			response=new UserObject();
			response.setStatus("ERROR");
			response.setError(new Error(e.getCode(),e.getMessage()));
		}
	catch (Exception e) {
		
		log.error("Error Occured during user registration");
		response=new UserObject();
		response.setStatus("ERROR");
		response.setError(new Error("SYS_002", "System not available this time"));
	}
		log.info("Finish register");
		return response;

	}
	
	public Response updateAccount(UserObject request,String captcha) {
		log.info("Start updateAccount");
		UserObject response=new UserObject();
		
		 try {
			 if(!validateCaptcha(captcha, request.getCaptcha())) {
					throw new AppException(new Error("SYS_006","Invalid Captcha"));
				}
			 User user=userRepository.findOne(request.getId());
			 user.setFirstname(request.getFirstName());
			 user.setEmail(request.getEmail());
			 user.setPassword(request.getPassword());
			 user.setUsername(request.getUserName());
			
		     User updatedUser=userRepository.save(user);
		   
		     response= prepareUserObject(updatedUser);
		 
			response.setStatus("SUCCESS");
			response.setSuccessMessage("Your Account Updated Successfully");
			
		 }
		 catch(AppException e) {
				log.error("Error Occured during user registration");
				response=new UserObject();
				response.setStatus("ERROR");
				response.setError(new Error(e.getCode(),e.getMessage()));
			}
		 catch(Exception exp) {
			 log.error("Error occurred while updating user");
			 response.setStatus("ERROR");
			 response.setError(new Error("SYS_002","System not available"));
		 }
			
		//} 
		
		log.info("Finish updateAccount");
		return response;

	}

	
	public void fetchCaptcha(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("=====================  ");
		try {
		response.setContentType("image/jpg");
		int iTotalChars = 6;
		int iHeight = 40;
		int iWidth = 150;
		Font fntStyle1 = new Font("Arial", Font.BOLD, 30);
		Random randChars = new Random();
		String sImageCode = (Long.toString(Math.abs(randChars.nextLong()), 36)).substring(0, iTotalChars);
		BufferedImage biImage = new BufferedImage(iWidth, iHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2dImage = (Graphics2D) biImage.getGraphics();
		int iCircle = 15;
		for (int i = 0; i < iCircle; i++) {
			g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
		}
		g2dImage.setFont(fntStyle1);
		for (int i = 0; i < iTotalChars; i++) {
			g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
			if (i % 2 == 0) {
				g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 24);
			} else {
				g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 35);
			}
		}
		OutputStream osImage = response.getOutputStream();
		ImageIO.write(biImage, "jpeg", osImage);
		g2dImage.dispose();
		HttpSession session = request.getSession();
		session.setAttribute("captcha_security", sImageCode);
		
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public Response fetchUser(String userName) {
		log.info("Start fetchUser");
		User user= userRepository.findByUsername(userName);
		UserObject resp=new UserObject();
		resp.setEmail(user.getEmail());
		resp.setFirstName(user.getFirstname());
		resp.setId(user.getId());
		resp.setUserName(user.getUsername());
		resp.setStatus("SUCCESS");
		
		log.info("Finish fetchUser");
		
		return resp;
		
	}
	


	
}
