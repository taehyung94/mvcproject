<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅방 목록</title>
<script>
window.onload = ()=>{
	const chatting_room_btn = document.getElementById("chatting_room_btn");
	chatting_room_btn.addEventListener('click', (e)=>{
		const req = new XMLHttpRequest();
		/* req.onreadystatechange = ()=>{
			console.log("통신 성공!!!");
		}; */
		console.log("클릭");
		let uri = "/mvcproject/chatting/makeroom?name="+document.getElementById("chatting_room").value;
		req.open('GET', uri, true);
		req.send();
		req.onload = ()=>{
			if(req.status==200){
				let res = JSON.parse(req.responseText);
				console.log(res);
				console.log("생성버튼 누름");
				if(res.status=="success"){
					location.reload();
				} else alert("채팅방 생성에 실패 했습니다.");
			} else{
				alert("잠시 후에 다시 시도해주세요");
			}
		}
	});
}



</script>
</head>
<body>
<table border="1">
<caption>채팅방 목록</caption>
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
원하는 채팅방을 만들어보세요<br>
<input type="text" id="chatting_room" name="chatting_room" placeholder="채팅방 이름 입력"><br>
<input type="button" value="만들기" id="chatting_room_btn">
</body>
</html>