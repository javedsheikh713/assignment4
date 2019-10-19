package javed.assignment4.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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


import javed.assignment4.dao.UserDao;
import javed.assignment4.dao.UserDaoImpl;
import javed.assignment4.model.Login;
import javed.assignment4.model.User;

@Service
public class UserService {

	@Autowired
	private UserDaoImpl userDaoImpl;
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	char data[][] = { { 'z', 'e', 't', 'c', 'o', 'd', 'e' }, { 'l', 'i', 'n', 'u', 'x' },
			{ 'f', 'r', 'e', 'e', 'b', 's', 'd' }, { 'u', 'b', 'u', 'n', 't', 'u' }, { 'j', 'e', 'e' } };
	int width = 150;
	int height = 50;

	public User validateUser(Login login) {
		
		log.info("Start Validate user request ");
		    User user=null;
			user=userDaoImpl.findByName(login.getUserName());
			if(user.getPassword().equals(login.getPassword())) {
				log.info("Finish Validate user request ");
			return user;
			}else {
				log.info("Finish Validate user request ");
				return null;
			}
	
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

	public String register(User user,HttpServletRequest request) {
		
		log.info("Start register");
		String responseString="INVALID_CAPTCHA";
		String captcha = request.getSession().getAttribute("captcha_security").toString();
		String verifyCaptcha = user.getCaptcha();
		if (validateCaptcha(captcha,verifyCaptcha)) {
			userDaoImpl.save(user);
			responseString= "SUCCESS";
		} 
		
		log.info("Finish register");
		return responseString;

	}
	
	public String updateAccount(User user,HttpServletRequest request) {
		log.info("Start updateAccount");
		String responseString="INVALID_CAPTCHA";
		String captcha = request.getSession().getAttribute("captcha_security").toString();
		String verifyCaptcha = user.getCaptcha();
		if (validateCaptcha(captcha,verifyCaptcha)) {
			userDaoImpl.update(user);
			responseString= "SUCCESS";
		} 
		
		log.info("Finish updateAccount");
		return responseString;

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
	
	public User getUserByName(String userName) {
		log.info("get By User name");
		
		return userDaoImpl.findByName(userName);
	}

	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}

	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}

	
}
