package edu.kh.jdbc.member.main.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import static edu.kh.jdbc.common.JDBCTemplate.*;
import edu.kh.jdbc.member.model.dto.Member;

public class MainDao {

	// JDBC 객체 참조 변수
	private Statement stmt; // SQL 수행, 결과 반환
	private PreparedStatement pstmt; // placeholder(위치홀더:?)를 포함한 SQL 세팅/수행/결과반환
	private ResultSet rs; // SELECT 수행 결과 저장
	
	private Properties prop;
	
	// 기본생성자 DAO 객체가 생성될 때 xml 파일 읽어와 Properties 객체 저장
	public MainDao() {
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("main-sql.xml"));
			// -> Properties 객체에
			//    key:value 형식으로 xml 내용이 저장됨
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/** 로그인 DAO(아이디, 비밀번호 일치 회원 조회)
	 * @param conn
	 * @param memberId
	 * @param memberPw
	 * @return
	 */
	public Member login(Connection conn, String memberId, String memberPw) throws Exception {
		
		// 1. 결과 저장용 변수 생성
		Member member = null;
		
		try {
			
			// 2. SQL 작성 후 수행
			String sql = prop.getProperty("login");
			
			// PreparedStatement 객체를 생성하고 SQL를 담아둠
			pstmt = conn.prepareStatement(sql);
			
			// placeholder에 맞는 값 대입
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);
			
			rs = pstmt.executeQuery(); // SELECT 수행 후 결과 반환 받기
			
			// 3. 조회 결과를 1행씩 접근해서 얻어오기
			if(rs.next()) {
				int memberNo = rs.getInt("MEMBER_NO");
				// String memberId = rs.getString("MEMBER_ID");
				// 입력받은 아이디 == 조회한 아이디
				String memberNme = rs.getString("MEMBER_NM");
				String memberGender = rs.getString("MEMBER_GENDER");
				String enrollDate = rs.getString("ENROLL_DT");
				
				// Member 객체 생성 후 값 세팅
				member = new Member();
				
				member.setMemberNo(memberNo);
				member.setMemberId(memberId);
				member.setMemberName(memberNme);
				member.setMemberGender(memberGender);
				member.setEnrollDate(enrollDate);
			}
			
		} finally {
			
			// 4. 사용한 JDBC 객체 자원 반환
			close(rs);
			close(pstmt);
		}
		
		// 5. 결과반환
		return member;
	}

	/**아이디 중검 검사 SQL 수행 DAO
	 * @param conn
	 * @param memberId
	 * @return
	 */
	public int idDuplicationcheck(Connection conn, String memberId) throws Exception {

		int result = 0;
		
		try {
			
			String sql = prop.getProperty("idDuplicationcheck");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1); // 첫번째 컬럼
			}
			
		} finally {
			
			close(rs);
			close(pstmt);
			
		}
		
		return result;
	}

	public int signUp(Connection conn, Member member) throws Exception {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("singUp");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberGender());
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			close(pstmt);
		}
		
		return result;
	}
	
}
