package mvcproject;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import mvc.service.ChatService;

@ServerEndpoint(value = "/chattingServer", configurator = HttpSessionConfigurator.class)
public class WebChatServer extends HttpServlet {
	private static Map<Long, Map<Session, EndpointConfig>> userMap = Collections.synchronizedMap(new HashMap<Long,Map<Session,EndpointConfig>>());
	private ChatService chatService = ChatService.getInstance();
	@OnMessage
	public void onMsg(String message, Session userSession) throws IOException, Exception{
		Long chatroom_id = getChatRoomId(userSession);
		Long member_id = getMemberId(userSession);
		chatService.insertChatting(member_id, chatroom_id, message);
		synchronized (userMap.get(chatroom_id)) {
			Iterator<Session> it = userMap.get(chatroom_id).keySet().iterator();
			String login_id = getLoginId(userSession);
			while(it.hasNext()){
				Session currentSession = it.next();
				if(!currentSession.equals(userSession)){
					currentSession.getBasicRemote().sendText(login_id + " : " + message);
				}
			}
		}
	}
	
	@OnOpen
	public void onOpen(Session userSession, EndpointConfig config){
		Long chatroom_id = getChatRoomId(userSession);
		
		if(!userMap.containsKey(chatroom_id)) {			
			userMap.put(chatroom_id, new HashMap<Session, EndpointConfig>());
			userMap.get(chatroom_id).put(userSession, config);
		} else {
			userMap.get(chatroom_id).put(userSession, config);
		}
		HttpSession session = (HttpSession)config.getUserProperties().get("session");
		String login_id = session.getAttribute("login_id").toString();
		synchronized (userMap.get(chatroom_id)) {
			Iterator<Session> it = userMap.get(chatroom_id).keySet().iterator();
			while(it.hasNext()){
				Session currentSession = it.next();
				try {
					currentSession.getBasicRemote().sendText(login_id + "님이 입장하셨습니다. 현재 사용자 " + userMap.get(chatroom_id).size() + "명");
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	@OnClose
	public void onClose(Session userSession) {
		Long chatroom_id = getChatRoomId(userSession);
		String login_id = getLoginId(userSession);
		userMap.get(chatroom_id).remove(userSession);
		synchronized (userMap.get(chatroom_id)) {
			Iterator<Session> it = userMap.get(chatroom_id).keySet().iterator();
			while(it.hasNext()){
				Session currentSession = it.next();
				try {
					currentSession.getBasicRemote().sendText(login_id + "님이 퇴장하셨습니다. 현재 사용자 " + userMap.get(chatroom_id).size() + "명");
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private Long getChatRoomId(Session userSession) {
		return Long.parseLong(userSession.getRequestParameterMap().get("chatroom_id").get(0));
	}
	
	private String getLoginId(Session userSession) {
		HttpSession session = (HttpSession)userMap.get(getChatRoomId(userSession)).get(userSession).getUserProperties().get("session");
		String login_id = session.getAttribute("login_id").toString();
		return login_id;
	}
	
	private Long getMemberId(Session userSession) {
		HttpSession session = (HttpSession)userMap.get(getChatRoomId(userSession)).get(userSession).getUserProperties().get("session");
		Long member_id = Long.parseLong(session.getAttribute("id").toString());
		return member_id;
	}
}