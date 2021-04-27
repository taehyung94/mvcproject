package mvc.model;

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
}
