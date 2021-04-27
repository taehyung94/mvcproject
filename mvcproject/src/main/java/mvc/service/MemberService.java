package mvc.service;

import java.sql.SQLException;
import java.util.List;

import mvc.model.ChatRoomDAO;
import mvc.model.ChatRoomDTO;
import mvc.model.MemberDAO;
import mvc.model.MemberDTO;

public class MemberService {
	private static final MemberService memberService = new MemberService();
	private MemberDAO memberDAO = MemberDAO.getInstance();
	private ChatRoomDAO chatRoomDAO = ChatRoomDAO.getInstance();
	private MemberService() {
		
	}
	public static MemberService getInstance() {
		return memberService;
	}
	public boolean idCheck(String login_id) throws Exception {
		return memberDAO.idCheck(login_id);
	}
	
	public void signUp(MemberDTO memberDTO) throws SQLException {
		memberDAO.signUp(memberDTO);
	}
	
	public Long login(MemberDTO memberDTO) throws SQLException {
		return memberDAO.login(memberDTO);
	}
	
	public List<ChatRoomDTO> getEnrollList(long id) throws Exception {
		return chatRoomDAO.getEnrollList(id);
	}
}