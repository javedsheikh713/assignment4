package javed.assignment4.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javed.assignment4.model.User;


@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	 final String INSERT_QUERY = "insert into users (id, username,email,firstname,password,role) values (:id, :username,:email,:firstname,:password,:role)";
	 final String UPDATE_QUERY = "update users set username=:username,email=:email,firstname=:firstname,password=:password where id=:id";
	
	
	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	@Override
	public User findByName(String name) {
		
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        
		String sql = "SELECT * FROM users WHERE username=:name";
		
        User result = namedParameterJdbcTemplate.queryForObject(
                    sql,
                    params,
                    new UserMapper());
                    
        //new BeanPropertyRowMapper(Customer.class));
       
      
        return result;
	}

	
	@Override
	public List<User> findAll() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sql = "SELECT * FROM users";
		
        List<User> result = namedParameterJdbcTemplate.query(sql, params, new UserMapper());
       
        return result;
        
	}
	
	@Override
    public int save(User user) {
        // Creating map with all required params
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("firstname", user.getFirstname());
        paramMap.put("username", user.getUsername());
        paramMap.put("password", user.getPassword());
        paramMap.put("email", user.getEmail());
        paramMap.put("id", UUID.randomUUID());
        paramMap.put("role", user.getRole());
        // Passing map containing named params
        return namedParameterJdbcTemplate.update(INSERT_QUERY, paramMap);  
    }
	
	@Override
    public int update(User user) {
        // Creating map with all required params
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("firstname", user.getFirstname());
        paramMap.put("username", user.getUsername());
        paramMap.put("password", user.getPassword());
        paramMap.put("id", user.getId());
        paramMap.put("email",user.getEmail());
       
        // Passing map containing named params
        return namedParameterJdbcTemplate.update(UPDATE_QUERY, paramMap);  
    }
	
	

	private static final class UserMapper implements RowMapper<User> {

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setUsername(rs.getString("username"));
			user.setEmail(rs.getString("email"));
			user.setFirstname(rs.getString("firstname"));
			user.setPassword(rs.getString("password"));
			user.setRole(rs.getString("role"));
			
			return user;
		}
	}

}