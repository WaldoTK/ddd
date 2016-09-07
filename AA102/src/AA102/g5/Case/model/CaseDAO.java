package AA102.g5.Case.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import AA102g5_2.member.model.MemberVO;
import idv.ron.server.main.Common;


public class CaseDAO implements CaseDAO_interface {

	// �@�����ε{����,�w��@�Ӹ�Ʈw ,�@�Τ@��DataSource�Y�i
//mem_no,mem_id,mem_pw,mem_nk,mem_name,sex,zip_code,address,phone,email,photo,acc_status,skills,mem_job,mem_intro,birthdate,tool
	public CaseDAO() {
		super();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = 
			"INSERT INTO case (case_no,case_name,case_status,mem_no,case_type,if_public,case_info,budget,case_LOC,pg_needed,md_needed,case_date,case_score,case_add_date,visited_count,applied_count) VALUES ('CA'||CASE_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT case_no,case_name,case_status,mem_no,case_type,case_info,budget,case_LOC,pg_needed,md_needed,to_char(case_date,'yyyy-mm-dd') case_date FROM CASE order by case_no";
	private static final String GET_ONE_STMT = 
			"SELECT case_no,case_name,case_status,mem_no,case_type,case_info,budget,case_LOC,pg_needed,md_needed,to_char(case_date,'yyyy-mm-dd') case_date  FROM CASE where case_no = ?";
	private static final String DELETE = 
			"DELETE FROM case where case_no = ?";
	private static final String UPDATE = 
			"UPDATE case set case_no=?,case_name=?,case_status=?,mem_no=?,case_type=?,if_public=?,case_info=?,budget=?,case_LOC=?,pg_needed=?,md_needed=?,case_date=?,case_score=?,case_add_date=?,visited_count=?,applied_count=? where case_no = ?";
	private static final String GET_SOME_STMT = 
			"SELECT case_name, case_type, case_info  FROM CASE order by case_no DESC ";
	
	private static final String GET_WHERE_STMT = 
			"SELECT case_name, case_type, case_info  FROM CASE where case_type = ?";
	@Override
	public int insert(CaseVO caseVO) {
		int count = 0;
		String sql = "INSERT INTO Case"
				+ "(case_no, MEM_NO, CASE_NAME, CASE_STATUS, CASE_TYPE, CASE_INFO, BUDGET, CASE_LOC, PG_NEEDED, MD_NEEDED, case_date ) "
				+ " VALUES(Member_sq.nextval,?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USER,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, caseVO.getMem_no());
			ps.setString(2, caseVO.getCase_name());
			//status:1 �N��o����
			ps.setInt(3, 1);
			ps.setString(4, caseVO.getCase_type());
			ps.setString(5, caseVO.getCase_info());
			ps.setString(6, caseVO.getBudget());
			ps.setString(7, caseVO.getCase_loc());
			ps.setString(8, caseVO.getPg_needed());
			ps.setString(9, caseVO.getMd_needed());
			ps.setDate(10, caseVO.getCase_date());		
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
	public void update(CaseVO caseVo) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(Integer case_no) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public CaseVO findByPrimaryKey(Integer case_no) {
		CaseVO caseVO = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = DriverManager.getConnection(Common.URL, Common.USER,
					Common.PASSWORD);
			ps = conn.prepareStatement(GET_ONE_STMT);
			ps.setInt(1,case_no); 
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				caseVO = new CaseVO();
				caseVO.setCase_no(rs.getInt("case_no"));
				caseVO.setMem_no(rs.getString("mem_no"));
				caseVO.setCase_name(rs.getString("case_name"));
				caseVO.setCase_status(rs.getInt("case_status"));
				
				caseVO.setCase_type(rs.getString("case_type"));

				
				caseVO.setCase_info(rs.getString("case_info"));
				caseVO.setBudget(rs.getString("budget"));
				caseVO.setCase_loc(rs.getString("case_loc"));
				caseVO.setPg_needed(rs.getString("pg_needed"));

				caseVO.setMd_needed(rs.getString("md_needed"));
				caseVO.setCase_date(rs.getDate("case_date"));
												
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return caseVO;
	}
	@Override
	public List<CaseVO> getAll() {
//		CaseVO caseVO = null;
//		Connection conn = null;
//		PreparedStatement ps = null;
//		try{
//			conn = DriverManager.getConnection(Common.URL, Common.USER,
//					Common.PASSWORD);
//			ps = conn.prepareStatement(GET_ALL_STMT);
//			ResultSet rs = ps.executeQuery();
//			List<CaseVO> caseList = new ArrayList<CaseVO>();
//			while(rs.next()){
//				caseVO = new CaseVO();
//				caseVO.setCase_no(rs.getInt("case_no"));
//				caseVO.setCase_name(rs.getString("case_name"));
//				caseVO.setCase_status(rs.getInt("case_status"));
//				caseVO.setMem_no(rs.getInt("mem_no"));
//				caseVO.setCase_type(rs.getString("case_type"));
//
//
//				caseVO.setCase_info(rs.getString("case_info"));
//				caseVO.setBudget(rs.getInt("budget"));
//				caseVO.setCase_loc(rs.getString("case_LOC"));
//				caseVO.setPg_needed(rs.getInt("pg_needed"));
//
//				caseVO.setMd_needed(rs.getInt("md_needed"));
//				caseVO.setCase_date(rs.getDate("case_date"));
//				caseList.add(caseVO); // Store the row in the list			
//			}
//			   return caseList;
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally {
//			try {
//				ps.close();
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
		return null;
	}
	@Override
	public List<CaseVO> getSome() {
		CaseVO caseVO = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DriverManager.getConnection(Common.URL, Common.USER,
					Common.PASSWORD);
			ps = conn.prepareStatement(GET_SOME_STMT);
			ResultSet rs = ps.executeQuery();
			List<CaseVO> caseList = new ArrayList<CaseVO>();
			while (rs.next()) {
				caseVO = new CaseVO();
				
				caseVO.setCase_type(rs.getString("case_type"));
				caseVO.setCase_name(rs.getString("case_name"));
				caseVO.setCase_info(rs.getString("case_info"));
				
				caseList.add(caseVO);
			}
			return caseList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	public List<CaseVO> getWhere(String condition) {
		CaseVO caseVO = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DriverManager.getConnection(Common.URL, Common.USER,
					Common.PASSWORD);
			ps = conn.prepareStatement(GET_WHERE_STMT);
			ps.setString(1, condition);
			ResultSet rs = ps.executeQuery();
			List<CaseVO> caseList = new ArrayList<CaseVO>();
			while (rs.next()) {
				caseVO = new CaseVO();
				
				caseVO.setCase_type(rs.getString("case_type"));
				caseVO.setCase_name(rs.getString("case_name"));
				caseVO.setCase_info(rs.getString("case_info"));
				caseList.add(caseVO);
			}
			return caseList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}

