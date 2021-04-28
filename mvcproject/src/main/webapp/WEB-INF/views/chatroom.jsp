<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%

String chatroom_id=request.getParameter("chatroom_id");
String login_id = session.getAttribute("login_id").toString();

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅방</title>
<script>
let lastChatId = -1;
let findAll = false;
window.onload = ()=>{
	let exit_btn = document.getElementById('exit_btn');
	exit_btn.addEventListener("click",e=>{
		location.href="/mvcproject/chatting/exit?chatroom_id=<%=chatroom_id %>";	
	});
	$("#chat-container").scroll(()=> {
		let container = $('#chat-container');
		if(container.scrollTop() == 0 && findAll == false){
			const req = new XMLHttpRequest();
			let uri = "/mvcproject/chatting/readChatting?chatroom_id="+chatroom_id+"&last_id="+lastChatId;
			req.open('GET', uri, true);
			req.send();
			req.onload = ()=>{
				if(req.status==200){
					let res = JSON.parse(req.responseText);
					let login_id = "${login_id}";
					if(res.status=="success"){
						//console.log(res.list);
						let readList = res.list;
						if(readList.length < 10){
							findAll = true;
						}
						let plusMsg = "";
						for(let i =0 ; i < readList.length; i++){
							if(login_id==readList[i].login_id){
								plusMsg += ("<div class='my-chat-box'><div class='chat my-chat'>" + readList[i].message + "</div><div class='chat-info'>"+ readList[i].send_date +"</div></div>");
								//$('#chat-container').append($chat);
							} else{
								plusMsg += ("<div class='chat-box'><div class='chat'>" + readList[i].message + "</div><div class='chat-info chat-box'>"+ readList[i].send_date +"</div></div>");
								//$('#chat-container').append($chat);
							}
							//$('#chat-container').scrollTop($('#chat-container')[0].scrollHeight+20);	
						}
						plusMsg += $('#chat-container').html();
						$('#chat-container').html(plusMsg);
						lastChatId = readList[0].id;
					} else alert("잠시 후에 다시 시도해주세요");
				} else{
					alert("잠시 후에 다시 시도해주세요");
				}
			}	
		}
	});
};


function parseNum(num){
    return (num<10)?'0'+num.toString():num.toString();
}

function parseDate(pd){
    let year = pd.getFullYear();
    let month = pd.getMonth()+1;
    let day =pd.getDate();
    
    let hour = pd.getHours();
    let mi = pd.getMinutes();
    let sec = pd.getSeconds();
    
    let res = year + "/" + parseNum(month) + "/" +parseNum(day) + " " + parseNum(hour) + ":" + parseNum(mi) + ":" + parseNum(sec);
    return res;
}

</script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>


<style type="text/css">
	*{
		font-family: 나눔고딕;
	}
	#messageWindow{
		background: black;
		color: greenyellow;
	}
	#inputMessage{
		width:500px;
		height:20px
	}
	#btn-submit{
		background: white;
		background: #F7E600;
		width:60px;
		height:30px;
		color:#607080;
		border:none;
	}
	
	#main-container{
		width:600px;
		height:680px;
		border:1px solid black;
		margin:10px;
		display: inline-block;
		
	}
	#chat-container{
		vertical-align: bottom;
		border: 1px solid black;
		margin:10px;
		min-height: 600px;
		max-height: 600px;
		overflow: scroll;
		overflow-x:hidden;
		background: #9bbbd4;
	}
	
	.chat{
		font-size: 20px;
		color:black;
		margin: 5px;
		min-height: 20px;
		padding: 5px;
		min-width: 50px;
		text-align: left;
        height:auto;
        word-break : break-all;
        background: #ffffff;
        width:auto;
        display:inline-block;
        border-radius: 10px 10px 10px 10px;
	}
	
	.notice{
		color:#607080;
		font-weight: bold;
		border : none;
		text-align: center;
		background-color: #9bbbd4;
		display: block;
	}

	.my-chat{
		text-align: right;
		background: #F7E600;
		border-radius: 10px 10px 10px 10px;
	}
	
	#bottom-container{
		margin:10px;
	}
	
	.chat-info{
		color:#556677;
		font-size: 10px;
		text-align: right;
		padding: 5px;
		padding-top: 0px;

	}
	
	.chat-box{
		text-align:left;
	}
	.my-chat-box{
		text-align: right;
	}
	
	
	
</style>
</head>
<body>
	<div id="main-container">
		<div id="chat-container">
			
		</div>
		<div id="bottom-container">
			<input id="inputMessage" type="text">
			<input id="btn-submit" type="submit" value="전송" ><br>
			<input id="exit_btn" type="button" value="나가기">
		</div>
	</div>
	<script>
		/* let list = ${list};
		let login_id = "${login_id}";
		for(let i =0 ; i < list.length; i++){
			if(login_id==list[i].login_id){
				let $chat = $("<div class='my-chat-box'><div class='chat my-chat'>" + list[i].message + "</div><div class='chat-info'>"+ list[i].send_date +"</div></div>");
				$('#chat-container').append($chat);
			} else{
				let $chat = $("<div class='chat-box'><div class='chat'>" + list[i].message + "</div><div class='chat-info chat-box'>"+ list[i].send_date +"</div></div>");
				$('#chat-container').append($chat);
			}
			$('#chat-container').scrollTop($('#chat-container')[0].scrollHeight+20);	
		} */
	</script>
</body>
<script type="text/javascript">
	
	let chatroom_id = <%=chatroom_id %>;
	var webSocket = new WebSocket("ws://localhost/mvcproject/chattingServer?chatroom_id="+chatroom_id);
	var textarea = document.getElementById("messageWindow");
	
	// 로컬에서 테스트할 때 사용하는 URL입니다.
// 	var webSocket = new WebSocket('ws://localhost/DevEricServers/webChatServer');
	var inputMessage = document.getElementById('inputMessage');
	
	webSocket.onerror = function(e){
		onError(e);
	};
	webSocket.onopen = function(e){
		onOpen(e);
	};
	webSocket.onmessage = function(e){
		onMessage(e);
	};
	
	
	function onMessage(e){
		var chatMsg = event.data;
		var date = new Date();
		var dateInfo = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
		let dateInfo2 = parseDate(date);
		if(chatMsg.substring(0,6) == 'server'){
			var $chat = $("<div class='chat notice'>" + chatMsg + "</div>");
			$('#chat-container').append($chat);
		}else{
			var $chat = $("<div class='chat-box'><div class='chat'>" + chatMsg + "</div><div class='chat-info chat-box'>"+ dateInfo2 +"</div></div>");
			$('#chat-container').append($chat);
		}
		
		
		$('#chat-container').scrollTop($('#chat-container')[0].scrollHeight+20);
	}
	
	function onOpen(e){
		let list = ${list};
		lastChatId = list[0].id;
		let login_id = "${login_id}";
		for(let i =0 ; i < list.length; i++){
			if(login_id==list[i].login_id){
				let $chat = $("<div class='my-chat-box'><div class='chat my-chat'>" + list[i].message + "</div><div class='chat-info'>"+ list[i].send_date +"</div></div>");
				$('#chat-container').append($chat);
			} else{
				let $chat = $("<div class='chat-box'><div class='chat'>" + list[i].message + "</div><div class='chat-info chat-box'>"+ list[i].send_date +"</div></div>");
				$('#chat-container').append($chat);
			}
			$('#chat-container').scrollTop($('#chat-container')[0].scrollHeight+20);	
		} 
	}
	
	function onError(e){
		alert(e.data);
	}
	
	function send(){
		var chatMsg = inputMessage.value;
		if(chatMsg == ''){
			return;
		}
		var date = new Date();
		var dateInfo = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
		let dateInfo2 = parseDate(date);
		var $chat = $("<div class='my-chat-box'><div class='chat my-chat'>" + chatMsg + "</div><div class='chat-info'>"+ dateInfo2 +"</div></div>");
		$('#chat-container').append($chat);
		webSocket.send(chatMsg);
		inputMessage.value = "";
		$('#chat-container').scrollTop($('#chat-container')[0].scrollHeight+20);
	}
	
	function readMessage(d){
		console.log(d);
	}
	
</script>

<script type="text/javascript">
	$(function(){
		$('#inputMessage').keydown(function(key){
			if(key.keyCode == 13){
				$('#inputMessage').focus();
				send();
			}
		});
		$('#btn-submit').click(function(){
			send();
		});
	})
</script>
</html>