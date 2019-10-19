package javed.assignment4.config;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javed.assignment4.model.User;
import javed.assignment4.service.CustomUserDetailsService;

@Component
public class JwtToken {

	

	    @Value("${jwt.secret}")
	    private String secret;
	    
	    @Value("${security.jwt.token.secret-key:secret}")
	    private String secretKey = "secret";  
	    
	    @Value("${security.jwt.token.expire-length:3600000}")
	    private long validityInMilliseconds = 3600000; // 1h
	    
	    @Autowired
	    CustomUserDetailsService customUserDetailsService;

	    /**
	     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
	     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
	     * 
	     * @param token the JWT token to parse
	     * @return the User object extracted from specified token or null if a token is invalid.
	     */
	    public User parseToken(String token) {
	        try {
	            Claims body = Jwts.parser()
	                    .setSigningKey(secretKey)
	                    .parseClaimsJws(token)
	                    .getBody();

	            User u = new User();
	            u.setUsername(body.getSubject());
	            

	            return u;

	        } catch (JwtException | ClassCastException e) {
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
	    public String generateToken(User u) {
	        Claims claims = Jwts.claims().setSubject(u.getUsername());
	        claims.put("username", u.getUsername());
	        claims.put("roles", u.getRole());

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
	    
	    public Authentication getAuthentication(String token) {
	    	
	    	System.out.println("token ; ==  "+token +"and   "+ customUserDetailsService);
	        User userDetails = (User) this.customUserDetailsService.loadUserByUsername(getUsername(token));
	        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(userDetails.getRole());
	        System.out.println("##################### "+authorityList.toString() +"#############");
	        return new UsernamePasswordAuthenticationToken(userDetails, "", authorityList);
	      //  return new UsernamePasswordAuthenticationToken(userDetails, "", null);
	    }
	    
	    public String getUsername(String token) {
	    	System.out.println("+++++ Token + "+ token +"  and ===  secretKey "+ secretKey);
	        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	    }
	    
	    public String resolveToken(HttpServletRequest req) {
	    	System.out.println("======== called here");
	        String bearerToken = req.getHeader("Authorization");
	        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
	            return bearerToken.substring(7, bearerToken.length());
	        }
	        return null;
	    }
	    
	    public boolean validateToken(String token) {
	        try {
	            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token); 
	            
	            if (claims.getBody().getExpiration().before(new Date())) {
	                return false;
	            }      
	            
	            return true;
	            
	        } catch (JwtException | IllegalArgumentException e) {
	            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
	        }
	    }
	    
	    public static void main(String[] args) {
			User user=new User();
			user.setUsername("shiza");
			JwtToken jwtToken=new JwtToken();
			String token=jwtToken.generateToken(user);
			System.out.println(token);
			System.out.println(jwtToken.validateToken(token));
			User us=jwtToken.parseToken(token);
			System.out.println(us.getUsername());
		}

		public CustomUserDetailsService getCustomUserDetailsService() {
			return customUserDetailsService;
		}

		public void setCustomUserDetailsService(CustomUserDetailsService customUserDetailsService) {
			this.customUserDetailsService = customUserDetailsService;
		}
	    
	    
	}

