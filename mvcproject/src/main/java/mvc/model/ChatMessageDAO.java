package mvc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ChatMessageDAO {
	private DataSource dataSource;
	private static ChatMessageDAO chatMessageDAO = new ChatMessageDAO();
	private ChatMessageDAO() {
		try {
			Context context = new InitialContext();
			Context envContext = (Context) context.lookup("java:comp/env");
			dataSource = (DataSource)envContext.lookup("jdbc/oracle2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static ChatMessageDAO getInstance() {
		return chatMessageDAO;
	}
	public void insertChatting(long member_id, long chatroom_id, String message) throws Exception{
		String query = "insert into chat_message values(chat_message_seq.nextval, ?, ?, ?, sysdate)";
		try  (Connection conn = dataSource.getConnection();
				 PreparedStatement ps = conn.prepareStatement(query)){
			ps.setLong(1, member_id);
			ps.setLong(2, chatroom_id);
			ps.setString(3, message);
			ps.executeUpdate();
		}
	}
	
	public List<ChatMessageDTO> getChattingList(long member_id, long chatroom_id) throws Exception {
		StringBuffer sb = new StringBuffer();		
		sb.append("select * from ");
		sb.append("(select rownum seq, dummy_data.* from (select mid, m.login_id, message, send_date from ");
		sb.append("		(select ");
		sb.append("		member_id, message, to_char(send_date,'YYYY/MM/DD HH24:MI:SS') send_date, id mid ");
		sb.append("		from chat_message ");
		sb.append("		where ");
		sb.append("		chatroom_id = ? and ");
		sb.append("		send_date > (select enroll_date from chat_enroll where member_id = ? and chatroom_id = ?)) info ");
		sb.append("		join members m ");
		sb.append("		on ");
		sb.append("		info.member_id = m.id order by mid desc) dummy_data) where seq between 1 and 10 order by mid asc ");
//		sb.append("select mid, m.login_id, message, send_date from ");
//		sb.append("(select ");
//		sb.append("member_id, message, to_char(send_date,'YYYY/MM/DD HH24:MI:SS') send_date, id mid ");
//		sb.append("from chat_message ");
//		sb.append("where ");
//		sb.append("chatroom_id = ? and ");
//		sb.append("send_date > (select enroll_date from chat_enroll where member_id = ? and chatroom_id = ?)) info ");
//		sb.append("join members m ");
//		sb.append("on ");
//		sb.append("info.member_id = m.id order by mid asc");
		String query = sb.toString();
		List<ChatMessageDTO> list = new ArrayList<ChatMessageDTO>();
		try  (Connection conn = dataSource.getConnection();
				 PreparedStatement ps = conn.prepareStatement(query)){
			ps.setLong(1, chatroom_id);
			ps.setLong(2, member_id);
			ps.setLong(3, chatroom_id);
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					ChatMessageDTO dto = new ChatMessageDTO();
					dto.setId(rs.getLong("mid"));
					dto.setLogin_id(rs.getString("login_id"));
					dto.setMessage(rs.getString("message"));
					dto.setSend_date(rs.getString("send_date"));
					list.add(dto);
				}
			}
		}
		return list;
	}
	
	public List<ChatMessageDTO> getChattingListWithPaging(long member_id, long chatroom_id, long last_id) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append("(select rownum seq, dummy_data.* from (select mid, m.login_id, message, send_date from ");
		sb.append("		(select ");
		sb.append("		member_id, message, to_char(send_date,'YYYY/MM/DD HH24:MI:SS') send_date, id mid ");
		sb.append("		from chat_message ");
		sb.append("		where ");
		sb.append("        id < ? and ");
		sb.append("		chatroom_id = ? and ");
		sb.append("		send_date > (select enroll_date from chat_enroll where member_id = ? and chatroom_id = ?)) info ");
		sb.append("		join members m ");
		sb.append("		on ");
		sb.append("		info.member_id = m.id order by mid desc) dummy_data) where seq between 1 and 10 order by mid asc");
		String query = sb.toString();
		List<ChatMessageDTO> list = new ArrayList<ChatMessageDTO>();
		try  (Connection conn = dataSource.getConnection();
				 PreparedStatement ps = conn.prepareStatement(query)){
			ps.setLong(1, last_id);
			ps.setLong(2, chatroom_id);
			ps.setLong(3, member_id);
			ps.setLong(4, chatroom_id);
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					ChatMessageDTO dto = new ChatMessageDTO();
					dto.setId(rs.getLong("mid"));
					dto.setLogin_id(rs.getString("login_id"));
					dto.setMessage(rs.getString("message"));
					dto.setSend_date(rs.getString("send_date"));
					list.add(dto);
				}
			}
		}
		return list;
	}
}
