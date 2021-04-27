<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<table border="1">
<caption>참여중인 채팅방</caption>
<tr>
<th>방번호</th>
<th>방이름</th>
</tr>
	<c:forEach var="dto" items="${list }">
	<tr>
	<td><a href="detail?no=${dto.id}">${dto.id }</a></td>
	<td>${dto.name }</td>
	</tr>
	</c:forEach>
</table>
<a href="/mvcproject/chatting/roomlist">다른 채팅방 보러가기</a>
</body>
</html>