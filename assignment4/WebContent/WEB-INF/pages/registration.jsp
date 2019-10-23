<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
        <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
            <title>Registration</title>
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        </head>
        <body>
            <form:form id="regForm" modelAttribute="user" action="registerProcess" method="post">
            
            <h3 align="center">User Registration : </h3>
            
                <table align="center">
                    
                    <tr>
                        <td>
                            <form:label path="firstname">Name</form:label>
                        </td>
                        <td>
                            <form:input path="firstname" name="firstname" id="firstname" required="true" />
                        </td>
                    </tr>
                   
                    <tr>
                        <td>
                            <form:label path="email">Email</form:label>
                        </td>
                        <td>
                            <form:input path="email" name="email" id="email"  required="true" />
                        </td>
                    </tr>
                   <tr>
                        <td>
                            <form:label path="username">Username</form:label>
                        </td>
                        <td>
                            <form:input path="username" name="username" id="username" required="true" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:label path="password">Password</form:label>
                        </td>
                        <td>
                            <form:password path="password" name="password" id="password"  required="true" />
                        </td>
                    </tr>
                   <tr>
				<td>Captcha</td>
				<td>
					<img id='captchaimage' src="${pageContext.request.contextPath }/captcha">
					<a href="javascript:;"
						title="change captcha text"
						onclick="document.getElementById('captchaimage').src = 'captcha?' + Math.random();  return false">
							<img  src="https://cdn3.iconfinder.com/data/icons/flat-icons-web/40/Refresh-48.png" />
					</a>
					<br>
					<input type="text" name="captcha" required="required" style="margin-top: 5px;">
					<br>
					${error }
				</td>
			</tr>
                    <tr>
                        <td></td>
                        <td>
                            <form:button id="register" name="register" class="btn btn-success">Register</form:button>
                        </td>
                        
                        <td>
                            <form:button id="register" name="register" class="btn btn-success">Reset</form:button>
                        </td>
                        
                         <td>
                           <a href="home" class="btn btn-success">Home</a>
                        </td>
                        
                    </tr>
                                       
                </table>
            </form:form>
        </body>
        </html>