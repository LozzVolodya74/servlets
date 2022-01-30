<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%-- MainServlet --%>

<html>
<body>
 <h1 align="left" style="color: red;">you are sure</h1> <br>

 	<form action="${pageContext.request.contextPath}/delete" method="post">
 		<input type="submit" value = "YES">
 	</form>
 	<form action="${pageContext.request.contextPath}/refresh">
         <button type="submit">NO</button>
    </form>
</body>
</html>