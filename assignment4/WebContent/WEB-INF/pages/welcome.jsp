<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>

<h2>${msg}</h2>
<h2>welcome : Mr ${user.getUsername()}</h2>
<a href="accountUpdate?username=${user.getUsername()}" class="btn btn-success">Update Account</a>
<br/>
<br/>
<a href="home" class="btn btn-success">LogOut</a>

<%
   
  request.getSession().setAttribute("Authorization", "Bearer "+response.getHeader("Authorization"));
 // request.setAttribute("Authorization", "Bearer "+response.getHeader("Authorization"));
 
  %>

</body>
</html>