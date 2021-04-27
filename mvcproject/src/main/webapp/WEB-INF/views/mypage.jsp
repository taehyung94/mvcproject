<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<script>
<%if(session.getAttribute("id") == null){%>
	location.href="/mvcproject";
<%} %>
</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table>
<caption>참여중인 채팅방</caption>

</table>
</body>
</html>