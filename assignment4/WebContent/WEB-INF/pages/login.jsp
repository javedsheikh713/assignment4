<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
            <title>Login</title>
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
			
        </head>
		
        <body>
            <form:form id="loginForm" modelAttribute="login" action="loginProcess" method="post">
                <table align="center">
                    <tr>
                        <td>
                            <form:label path="userName">UserName: </form:label>
                        </td>
                        <td>
                            <form:input path="userName" name="userName" id="userName" required="true"  />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:label path="password">Password:</form:label>
                        </td>
                        <td>
                            <form:password path="password" name="password" id="password" required="true"  />
                        </td>
                    </tr>
                    <tr>
				<td>Captcha</td>
				<td>
					<img id="captchaimage" src="${pageContext.request.contextPath }/captcha">
					
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
                        <td align="left">
                            <form:button id="login" name="login">Login</form:button>
                        </td>
                    </tr>
                    <tr></tr>
                    <tr>
                        <td></td>
                        <td><a href="home">Home</a>
                        </td>
                    </tr>
                </table>
            </form:form>
            <table align="center">
                <tr>
                    <td style="font-style: italic; color: red;">${message}</td>
                </tr>
            </table>
			
						
        </body>
        </html>