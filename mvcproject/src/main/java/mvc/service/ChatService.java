package mvc.service;

import java.util.List;

import mvc.model.ChatMessageDAO;
import mvc.model.ChatMessageDTO;
import mvc.model.ChatRoomDAO;
import mvc.model.ChatRoomDTO;
import mvc.model.MemberDAO;

public class ChatService {
	private static final ChatService chatService = new ChatService();
	private ChatRoomDAO chatRoomDAO = ChatRoomDAO.getInstance();
	private ChatMessageDAO chatMessageDAO = ChatMessageDAO.getInstance();
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
	
	public int findIt(long chatroom_id) throws Exception {
		return chatRoomDAO.findIt(chatroom_id);
	}
	
	public void enroll(long member_id, long chatroom_id) throws Exception {
		chatRoomDAO.enroll(member_id, chatroom_id);
	}
	
	public List<ChatMessageDTO> getChattingList(long member_id, long chatroom_id) throws Exception{
		return chatMessageDAO.getChattingList(member_id, chatroom_id);
	}
	
	public boolean checkEnrollMember(long member_id, long chatroom_id) throws Exception {
		return chatRoomDAO.checkEnrollMember(member_id, chatroom_id);
	}
	
	public void insertChatting(long member_id, long chatroom_id, String message) throws Exception {
		chatMessageDAO.insertChatting(member_id, chatroom_id, message);
	}
	
	public void deleteEnroll(long member_id, long chatroom_id) throws Exception {
		chatRoomDAO.deleteEnroll(member_id, chatroom_id);
	}
	
	public List<ChatMessageDTO> getChattingListWithPaging(long member_id, long chatroom_id, long last_id) throws Exception {
		return chatMessageDAO.getChattingListWithPaging(member_id, chatroom_id, last_id);
	}
}
