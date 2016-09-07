package AA102g5_2.member.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



import idv.ron.server.main.Common;


public class MemberDAO implements MemberDAO_interface {
	
	public MemberDAO() {
		super();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(MemberVO memberVO) {
		int count = 0;
		String sql = "INSERT INTO Member"
				+ "(id, account, password, nickname, name, phone, status ) "
				+ "VALUES(Member_sq.nextval,?, ?, ?, ?, ?, ? )";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USER,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, memberVO.getAccount());
			ps.setString(2, memberVO.getPassword());
			ps.setString(3, memberVO.getNickname());
			ps.setString(4, memberVO.getName());
			ps.setString(5, memberVO.getPhone());
			ps.setString(6, memberVO.getStatus());
			
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					// When a Statement object is closed,
					// its current ResultSet object is also closed
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
		
	}

	@Override
	public int update(MemberVO memberVO, byte[] image) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public MemberVO findByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getImage(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	
	
	@Override
	public MemberVO login(String account) {
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id, account, password, nickname, name, phone, status FROM member where account = ? ";
		try {
			con = DriverManager.getConnection(Common.URL, Common.USER,
					Common.PASSWORD);
			
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, account);

			rs = pstmt.executeQuery();
           
			while (rs.next()) {
				
				// empVo ¤]ºÙ¬° Domain objects
				memberVO = new MemberVO();
				memberVO.setId(rs.getString("id"));
			    System.out.println("id~~~~~~~~~~~"+rs.getString("id"));
				memberVO.setAccount(rs.getString("account"));
				memberVO.setPassword(rs.getString("password"));
				memberVO.setNickname(rs.getString("nickname"));
				memberVO.setName(rs.getString("name"));
				memberVO.setPhone(rs.getString("phone"));
				memberVO.setStatus(rs.getString("status"));
				 System.out.println("Status~~~~~~~~~~~"+rs.getString("status"));
			}

			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					// When a Statement object is closed,
					// its current ResultSet object is also closed
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return memberVO;
		
		
	}

	
		

}
