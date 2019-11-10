package javed.assignment5.common;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	public static String convertToJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		String responseString=null;
		try {
		responseString=mapper.writeValueAsString(object);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return responseString;
		
	}
}
