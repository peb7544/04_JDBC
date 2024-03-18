package edu.kh.jdbc.member.main.service;

import java.sql.Connection;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import edu.kh.jdbc.member.main.dao.MainDao;
import edu.kh.jdbc.member.model.dto.Member;

public class MainService {

	MainDao dao = new MainDao();

	/** 로그인 Service
	 * @param memberId
	 * @param memberPw
	 * @return member
	 */
	public Member login(String memberId, String memberPw) throws Exception {
		
		// 1. Connection 생성
		Connection conn = getConnection();
		
		// 2. DAO 호출
		Member member = dao.login(conn, memberId, memberPw);
		
		// 3. Conncetion 반환
		close(conn);
		
		// 4. 결과 반환
		return member;
	}

	/** 아이디 중복 검사 Service
	 * @return
	 */
	public int idDuplicationcheck(String memberId) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.idDuplicationcheck(conn, memberId);
		
		close(conn);
		
		return result;
	}

	/** 회원가입 서비스
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public int signUp(Member member) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.signUp(conn, member); // INSERT 처리
		
		// 트랜잭션 처리
		if(result > 0) commit(conn);
		else           rollback(conn);
		
		close(conn);
		
		return result;
	}
}
