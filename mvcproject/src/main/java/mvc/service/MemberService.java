package mvc.service;

import java.sql.SQLException;

import mvc.model.MemberDAO;
import mvc.model.MemberDTO;

public class MemberService {
	private static final MemberService memberService = new MemberService();
	private MemberDAO memberDAO = MemberDAO.getInstance();
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
}