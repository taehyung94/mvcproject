package mvc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ChatRoomDAO {
	private DataSource dataSource;
	private static ChatRoomDAO chatRoomDAO = new ChatRoomDAO();
	private ChatRoomDAO() {
		try {
			Context context = new InitialContext();
			Context envContext = (Context) context.lookup("java:comp/env");
			dataSource = (DataSource)envContext.lookup("jdbc/oracle2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static ChatRoomDAO getInstance() {
		return chatRoomDAO;
	}
	public List<ChatRoomDTO> getEnrollList(long id) throws Exception{
		List<ChatRoomDTO> list = new ArrayList<ChatRoomDTO>();
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("cr.id id, cr.name ");
		sb.append("from ");
		sb.append("members m ");
		sb.append("join chat_enroll ce ");
		sb.append("on m.id = ? and m.id = ce.member_id ");
		sb.append("join chatroom cr ");
		sb.append("on ce.chatroom_id = cr.id");
		String query = sb.toString();
		try  (Connection conn = dataSource.getConnection();
				 PreparedStatement ps = conn.prepareStatement(query)){
			ps.setLong(1, id);
			try(ResultSet rs = ps.executeQuery();) {
				while(rs.next()) {
					ChatRoomDTO dto = new ChatRoomDTO();
					dto.setChatroom_id(rs.getLong(1));
					dto.setName(rs.getString(2));
					list.add(dto);
				}
			}
		}
		return list;
	}
}
