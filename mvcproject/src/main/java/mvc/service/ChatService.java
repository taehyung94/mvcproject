package mvc.service;

import java.util.List;

import mvc.model.ChatRoomDAO;
import mvc.model.ChatRoomDTO;
import mvc.model.MemberDAO;

public class ChatService {
	private static final ChatService chatService = new ChatService();
	private ChatRoomDAO chatRoomDAO = ChatRoomDAO.getInstance();
	private ChatService() {
		
	}
	public static ChatService getInstance() {
		return chatService;
	}
	
	public List<ChatRoomDTO> getChattingRoomList() throws Exception{
		return chatRoomDAO.getChattingRoomList();
	}
	
	public void makeChattingRoom(String name, long member_id) throws Exception {
		System.out.println("등록 서비스 호출");
		long chatroom_id = chatRoomDAO.makeChattingRoom(name);
		chatRoomDAO.enroll(member_id, chatroom_id);
	}
}
