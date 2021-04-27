package mvcproject;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class WebSocket {
	@OnOpen
	public void handleOn(Session session) {
		System.out.println("client is now connected");
	}
	
	@OnMessage
	public String handleMessage(String msg) {
		System.out.println("receive from client : " + msg);
		String replymessage = "echo " + msg;
		// 에코 메시지를 콘솔에 출력한다.
		
		System.out.println("send to client : "+replymessage);
		// 에코 메시지를 브라우저에 보낸다.
		return replymessage;
	}
	
	@OnClose
	public void handleClose() {
		// 콘솔에 접속 끊김 로그를 출력한다.
		System.out.println("client is now disconnected...");
	}
	
	// WebSocket과 브라우저 간에 통신 에러가 발생하면 요청되는 함수.
	@OnError
	public void handleError(Throwable t) {
		//콘솔에 에러를 표시한다.
		t.printStackTrace();
	}
}
