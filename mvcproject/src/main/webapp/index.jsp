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
</head>
<body>
<form action="member/login">
아이디:<input type="text" name="login_id"><br>
비밀번호:<input type="password" name="login_password"><br>
<input type="submit" value="로그인">
</form>
<a href="member/signup">회원가입</a>
</body>
</html>