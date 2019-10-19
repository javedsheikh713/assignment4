<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>

<h2>${msg}</h2>
<h2>welcome : Mr ${user.getUsername()}</h2>
<a href="accountUpdate?username=${user.getUsername()}">Update Account</a>
<br/>
<a href="logout?username=${user.getUsername()}">LogOut</a>
</body>
</html>