package edu.kh.jdbc.run;

import edu.kh.jdbc.model.service.TestService;
import edu.kh.jdbc.model.vo.TestVO;

public class Run2 {

	public static void main(String[] args) {
		
		// TB_TSET 테이블에 한번에 3행 삽입
		TestService service = new TestService();
		
		TestVO vo1 = new TestVO(70, "제목70", "내용70");
		TestVO vo2 = new TestVO(80, "제목80", "내용80");
		TestVO vo3 = new TestVO(90, "제목90", "내용90");
		
		try {
			
			int result = service.insert(vo1, vo2, vo3);
			
			if(result > 0) {
				System.out.println("insert 성공");
			} else {
				System.out.println("insert 실패...");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
