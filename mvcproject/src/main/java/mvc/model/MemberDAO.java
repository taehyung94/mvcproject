package mvc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private DataSource dataSource;
	private static MemberDAO memberDAO = new MemberDAO();
	private MemberDAO() {
		try {
			Context context = new InitialContext();
			Context envContext = (Context) context.lookup("java:comp/env");
			dataSource = (DataSource)envContext.lookup("jdbc/oracle2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static MemberDAO getInstance() {
		return memberDAO;
	}
	
	public boolean idCheck(String login_id) throws SQLException {
		// TODO Auto-generated method stub
		String query = "select count(*) from members where login_id = ?";
		int r = 0;
		try (Connection conn = dataSource.getConnection();
				 PreparedStatement ps = conn.prepareStatement(query)){
			ps.setString(1, login_id);
			try(ResultSet rs = ps.executeQuery();) {
				rs.next();
				r = rs.getInt(1);
			}
			
		}
		return r == 1 ? false:true;
	}
	
	public void signUp(MemberDTO memberDTO) throws SQLException{
		String query = "insert into members values(member_seq.nextval,?,?)";
		try (Connection conn = dataSource.getConnection();
				 PreparedStatement ps = conn.prepareStatement(query)){
			ps.setString(1,memberDTO.getLoginId());
			ps.setString(2, memberDTO.getLoginPassword());
			ps.executeUpdate();
		}
	}
}
