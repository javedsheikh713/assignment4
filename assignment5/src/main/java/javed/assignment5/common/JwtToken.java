package javed.assignment5.common;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javed.assignment5.request.UserRequest;

@Component
public class JwtToken {

	
	    private String secretKey = "secret";  
	    
	  
	    private long validityInMilliseconds = 3600000; // 1h
	    
	   

	    /**
	     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
	     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
	     * 
	     * @param token the JWT token to parse
	     * @return the User object extracted from specified token or null if a token is invalid.
	     */
	    public UserRequest parseToken(String token) {
	        try {
	            Claims body = Jwts.parser()
	                    .setSigningKey(secretKey)
	                    .parseClaimsJws(token)
	                    .getBody();

	            UserRequest u = new UserRequest();
	            u.setUserName(body.getSubject());
	            

	            return u;

	        } catch (Exception e) {
	            return null;
	        }
	    }

	    /**
	     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
	     * User object. Tokens validity is infinite.
	     * 
	     * @param u the user for which the token will be generated
	     * @return the JWT token
	     */
	    public String generateToken(String userName,String roles) {
	        Claims claims = Jwts.claims().setSubject(userName);
	        claims.put("userName", userName);
	        claims.put("roles", roles);

	        Date now = new Date();
	        Date validity = new Date(now.getTime() + validityInMilliseconds); 
	        
	        return Jwts.builder()//
	            .setClaims(claims)//
	            .setIssuedAt(now)//
	            .setExpiration(validity)//
	            .signWith(SignatureAlgorithm.HS256, secretKey)//
	            .compact();
	        	       
	    }
	    
	    @PostConstruct
	    protected void init() {
	        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	    }
	    
	/*
	 * public Authentication getAuthentication(String token) {
	 * 
	 * System.out.println("token ; ==  "+token +"and   "+ customUserDetailsService);
	 * User userDetails = (User)
	 * this.customUserDetailsService.loadUserByUsername(getUsername(token));
	 * List<GrantedAuthority> authorityList =
	 * AuthorityUtils.commaSeparatedStringToAuthorityList(userDetails.getRole());
	 * System.out.println("##################### "+authorityList.toString()
	 * +"#############"); return new
	 * UsernamePasswordAuthenticationToken(userDetails, "", authorityList); //
	 * return new UsernamePasswordAuthenticationToken(userDetails, "", null); }
	 */
	    
	    public String getUsername(String token) {
	    	System.out.println("+++++ Token + "+ token +"  and ===  secretKey "+ secretKey);
	        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	    }
	    
	    public String resolveToken(HttpServletRequest req) {
	    	
	       // String bearerToken = req.getHeader("Authorization");
	    	
	    	String bearerToken =(String) req.getSession().getAttribute("Authorization");
	        System.out.println("======== called here   "+ bearerToken);
	        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
	            return bearerToken.substring(7, bearerToken.length());
	        }
	        return null;
	    }
	    
	    public boolean validateToken(String token) throws Exception {
	        try {
	            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token); 
	            
	            if (claims.getBody().getExpiration().before(new Date())) {
	                return false;
	            }      
	            
	            return true;
	            
	        } catch (Exception e) {
	            throw new Exception("Expired or invalid JWT token");
	        }
	    }
	    
	/*
	 * public static void main(String[] args) { User user=new User();
	 * user.setUsername("shiza"); JwtToken jwtToken=new JwtToken(); String
	 * token=jwtToken.generateToken(user); System.out.println(token);
	 * System.out.println(jwtToken.validateToken(token)); User
	 * us=jwtToken.parseToken(token); System.out.println(us.getUsername()); }
	 */
		
	    
	    
	}

