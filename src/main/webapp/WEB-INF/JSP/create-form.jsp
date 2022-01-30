<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- CreateUserServlet or UpdateServlet--%>

<html>
<head>
    <meta charset="UTF-8">
    <title>CREATE/UPDATE NEW USER</title>
</head>

<body>

<h2 align="right">Admin ${name} (<a href="${pageContext.request.contextPath}/main">logout</a>)</h2> <br>

<form action="${pageContext.request.contextPath}/${patch}" method="post">
    <h2 style="color: green;">${massage }</h2>
    <h3 style="color: blue;">
        <div>
            <label for="text">LOGIN</label>
            <c:if test="${update != null}">
                <input type="text" value="${log_ph}" readonly>
            </c:if>
            <c:if test="${update == null}">
                <input type="text" id="login" name="login" class="form-control" value="${log_ph}" >
            </c:if>

            <c:if test="${ERRORS['login'] != null}">
                <div class="invalid-feedback" style="color: red;">
                        ${ERRORS['login']}
                </div>
            </c:if>
        </div> <br>

        <div>
            <label for="text">PASSWORD</label>
            <input type="password" id="password" name="password" class="form-control" value="${pas_ph}">
            <c:if test="${ERRORS['password'] != null}">
                <div class="invalid-feedback" style="color: red;">
                        ${ERRORS['password']}
                </div>
            </c:if>
        </div> <br>

        <div>
            <label for="text">PASSWORD again</label>
            <input type="password" id="passwordAgain" name="passwordAgain" class="form-control"
                   value="${pas_ph}" >
            <c:if test="${ERRORS['password'] != null}">
                <div class="invalid-feedback" style="color: red;">
                        ${ERRORS['password']}
                </div>
            </c:if>
        </div> <br>

        <div>
            <label for="text">EMAIL</label>
            <input type="text" id="email" name="email" class="form-control" value="${email_ph}">
            <c:if test="${ERRORS['email'] != null}">
                <div class="invalid-feedback" style="color: red;">
                        ${ERRORS['email']}
                </div>
            </c:if>
        </div> <br>

        <div>
            <label for="text">FIRST NAME</label>
            <input type="text" name="firstName" class="form-control" value="${first_ph}" >
            <c:if test="${ERRORS['first'] != null}">
                <div class="invalid-feedback" style="color: red;">
                        ${ERRORS['first']}
                </div>
            </c:if>
        </div> <br>

        <div>
            <label for="text">LAST NAME</label>
            <input type="text" name="lastName" class="form-control" value="${last_ph}">
            <c:if test="${ERRORS['last'] != null}">
                <div class="invalid-feedback" style="color: red;">
                        ${ERRORS['last']}
                </div>
            </c:if>
        </div> <br>

        <div>
            <label for="text">BIRTHDAY</label>
            <input type="date" path="dueDate" class= "date" name="birthday" class="form-control" value="${dob_ph}">
            <c:if test="${ERRORS['dob'] != null}">
                <div class="invalid-feedback" style="color: red;">
                        ${ERRORS['dob']}
                </div>
            </c:if>
        </div>
        <br>
        <div>
            <label for="text">ROLE</label>
            <select id="isRole" name="isRole">
                <option value="user">user</option>
                <option value="admin">admin</option>
            </select>
        </div>
    </h3>
    <div>
        <button>OK</button>
    </div>
</form>

<form action="${pageContext.request.contextPath}/admin/refresh">
    <button type="submit">CANCEL</button>
</form>
</body>
</html>

<script language='javascript' type='text/javascript'>
    function check(input) {
        if (input.value != document.getElementById('password').value) {
            input.setCustomValidity('Password Must be Matching.');
        } else {
            input.setCustomValidity('');
        }
    }
</script>