<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>

<%-- MainServlet --%>
<html>
<body>
<h1 align="center" style="color: red;"> ${massage}</h1> <br>
	<form action="${pageContext.request.contextPath}/login" method="post">
	<h1 style="color: green;">Please INPUT YOUR LOGIN AND PASSWORD</h1>
		<h2 style="color: blue;"> INPUT LOGIN <input type="text" name="login"> </h2>
		<h2 style="color: blue;"> INPUT PASSWORD: <input type="password" name="password"> </h2> <br>
		<input type="submit" value = "sign in">
	</form>
</body>
</html>