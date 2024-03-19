package edu.kh.jdbc.board.mode.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.board.mode.dao.BoardDAO;
import edu.kh.jdbc.board.mode.dto.Board;
import static edu.kh.jdbc.common.JDBCTemplate.*;

public class BoardService {

	BoardDAO dao = new BoardDAO();

	/** 게시글 목록 조회 서비스
	 * @return
	 */
	public List<Board> selectAllBoard() throws Exception {
		
		// 커넥션 생성
		Connection conn = getConnection();
		
		// DAO 메서드 호출
		List<Board> boardList = dao.selectAllBoard(conn);
		
		// 커넥션 반환
		close(conn);
		
		// 결과 반환
		return boardList;
	}

	/** 게시글 상세 조회 서비스
	 * @param input
	 * @param memberNo
	 * @return
	 */
	public Board selectBoard(int input, int memberNo) throws Exception {
		
		// 1. 커넥션 생성
		Connection conn = getConnection();
		
		// 2. 게시글 상세 조회 DAO 호출
		Board board = dao.selectBoard(conn, input);
		
		// 3. 게시글 조회된 경우 
		if(board != null) {
			
			// 4. 조회수 증가
			// 단, 게시글 작성자와 로그인한 회원이 다를 경우에만 증가
			if(board.getMemberNo() != memberNo) {
				// 조회한 게시글 작성한 회원번호 != 로그인한 회원번호
				
				// 5. 조회수 증가 DAO 메서드 호출(UPDATE)
				int result = dao.updateReadCount(conn, input);
				
				// 6. 트렌젝션 제어 처리 + 데이터 동기화 처리
				if(result > 0) {
					commit(conn);
					
					// 조회된 bord의 조회수 0
					// DB의 조회수는 1
					// -> 조회 결과인 board의 조회수도 1증가
					board.setReadCount(board.getReadCount() + 1);
					
				} else {
					rollback(conn);
				}
				
			}
			
		}
		
		// 7. 커넥션 반환
		close(conn);
		
		return board;
	}
}
