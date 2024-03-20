package edu.kh.jdbc.board.mode.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.board.mode.dto.Comment;
import static edu.kh.jdbc.common.JDBCTemplate.*;

public class CommentDAO {
	
	// JDBC 객체 참조 변수
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// XML에 작성된 SQL을 읽어와 저장할 객체를 참조하는 변수
	private Properties prop;
	
	public CommentDAO() { // 기본 생성자
		
		try {
			
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("comment-sql.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 댓글 목록 조히
	 * @param input 
	 * @param conn 
	 * @return
	 */
	public List<Comment> selectCommentList(Connection conn, int input) throws Exception {
		
		// 결과 저장용 객체 생성
		List<Comment> commentList  = new ArrayList<Comment>();
		
		try {
			// SQL 작성
			String sql = prop.getProperty("selectCommentList");
			
			// SQL 수행 후 결과 반환 받기
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, input);
			
			rs = pstmt.executeQuery();
			
			// 1행씩 접근하여 컬럼값을 얻어와 옮겨담기
			while(rs.next()) {
				
				Comment c = new Comment();
				
				c.setCommentNo(rs.getInt(1));
				c.setCommentContent(rs.getString(2));
				c.setMemberNo(rs.getInt(3));
				c.setMemberName(rs.getString(4));
				c.setCreateDate(rs.getString(5));
				
				commentList.add(c);
			}
			
		} finally {
			
			close(rs);
			close(pstmt);
			
		}
		
		return commentList;
	}

	/** 댓글 등록 DAO
	 * @param conn
	 * @param boardNo
	 * @param commentContent
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int insertComment(Connection conn, int boardNo, String commentContent, int memberNo) throws Exception {
		
		// 결과 저장용 객체 생성
		int result = 0;
		
		try {
			
			// SQL 작성
			String sql = prop.getProperty("insertComment");
			
			// SQL 수행 후 결과 반환받기
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, commentContent);
			pstmt.setInt(2, memberNo);
			pstmt.setInt(3, boardNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			close(pstmt);
			
		}

		return result;
	}

	/** 댓글 확인 DAO
	 * @param conn
	 * @param commentNo
	 * @param boardNo
	 * @param memberNo
	 * @return check
	 * @throws Exception
	 */
	public int checkCommentNo(Connection conn, int commentNo, int boardNo, int memberNo) throws Exception {
		
		int check = 0;
		
		try {
			
			// SQL 작성
			String sql = prop.getProperty("checkCommentNo");
			
			// SQL 수행 후 결과 반환받기
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentNo);
			pstmt.setInt(2, memberNo);
			pstmt.setInt(3, boardNo);
			
			rs = pstmt.executeQuery();
			
			// 1행 접근하여 컬럼값을 얻어와 옮겨담기
			if(rs.next()) check = rs.getInt(1);
			
		} finally {
			
			close(rs);
			close(pstmt);
		}
		
		return check;
	}

	/** 댓글 수정 DAO
	 * @param conn
	 * @param commentNo
	 * @param commentContent
	 * @return
	 * @throws Exception
	 */
	public int updateComment(Connection conn, int commentNo, String commentContent) throws Exception {
		
		int result = 0;
		
		try {
			
			// SQL 작성
			String sql = prop.getProperty("updateComment");
			
			// SQL 수행 후 결과 반환받기
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, commentContent);
			pstmt.setInt(2, commentNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			close(pstmt);
			
		}
		
		
		return result;
	}

	/** 댓글 삭제 DAO
	 * @param conn
	 * @param commentNo
	 * @return result
	 */
	public int deleteComment(Connection conn, int commentNo) throws Exception {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("deleteComment");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			close(pstmt);
		}
		
		return result;
	}

}
