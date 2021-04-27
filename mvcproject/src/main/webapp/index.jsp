<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<script>
<% if (session.getAttribute("id") != null) {%>
location.href="/mvcproject/member/mypage";
<%}%>
</script>
</head>
<body>
<form action="/mvcproject/member/login" method="post">
아이디:<input type="text" name="login_id" required="required"><br>
비밀번호:<input type="password" name="login_password" required="required"><br>
<input type="submit" value="로그인">
</form>
<a href="member/signup">회원가입</a>
</body>
</html>