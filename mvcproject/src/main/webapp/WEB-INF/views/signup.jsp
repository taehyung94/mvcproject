<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>

window.onload = ()=>{
	function pwd_check(){
		return document.getElementById("login_password_check").value == document.getElementById("login_password").value;
	}
	const pw_check_log = document.getElementById('pwd_check_log');
	const login_password_check = document.getElementById("login_password_check");
	const login_password = document.getElementById("login_password");
	const login_id = document.getElementById("login_id");
	const id_check_btn = document.getElementById("id_check_btn");
	let id_check = false;
	const form = document.getElementById("form");
	login_id.addEventListener("keyup", (e)=>{
		id_check = false;
	});
	login_password_check.addEventListener("keyup",e=>{
		console.log(login_password_check.value);
		  if(pwd_check()){
			pw_check_log.innerHTML="비밀번호가 일치합니다.";
		} else{
			pw_check_log.innerHTML="비밀번호가 일치하지않습니다.";
		} 
	});
	form.addEventListener('submit', e=>{
		if(!pwd_check() || !id_check){
			alert("아이디 중복확인 또는 비밀번호 확인을 해주세요!");
			e.preventDefault();
		}
	})
	
	id_check_btn.addEventListener('click', (e)=>{
		const req = new XMLHttpRequest();
		/* req.onreadystatechange = ()=>{
			console.log("통신 성공!!!");
		}; */
		let uri = "idcheck?loginId="+document.getElementById("login_id").value;
		req.open('GET', uri, true);
		req.send();
		req.onload = ()=>{
			if(req.status==200){
				let res = JSON.parse(req.responseText);
				if(res.status=="success"){
					alert("중복확인 성공!");
					id_check = true;
				} else if(res.status=="redundant"){
					alert("중복된 아이디입니다!");
					id_check = false;
				} else alert("잠시 후에 다시 시도해주세요");
			} else{
				alert("잠시 후에 다시 시도해주세요");
			}
		}
	});
};

</script>
</head>
<body>
<form action="signupAction" id="form" method="post">
아이디:<input type="text" name="login_id" id="login_id"> &nbsp; <input type="button" id="id_check_btn" value="중복확인"><br>
비밀번호:<input type="password" name="login_password" id="login_password"><br>
비밀번호확인:<input type="password" name="login_password_check" id = "login_password_check"><span id="pwd_check_log"></span><br>
<input type="submit" id="submit_btn" value="회원가입">
</form>
</body>
</html>