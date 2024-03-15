package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Employee;

public class JDBCExample4 {
	
	public static void main(String[] args) {
		
		// 직급명, 급여를 입력받아
		// 해당 직급에서 입력 받은 급여보다 많이 받는 사원의
		// 이름, 직급명, 급여, 연봉을 조회하여 출력
		
		// 단, 조회 결과가 없으면 "조회 결과 없음" 출력
		
		// 조회 결과가 있으면 아래와 같이 출력
		// 직급명 입력 : 부사장
		// 급여 입력 : 5000000
		// 송종기 / 부사장 / 6000000 / 72000000
		
		// Employee(empName, jobName, salary, annualIncome)
		
		// JDBC 객체 참조 변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		Scanner sc = new Scanner(System.in);
		
		try {
			
			System.out.print("직급명 입력 : ");
			String input1 = sc.nextLine();
			
			System.out.print("급여 입력 : ");
			int input2 = sc.nextInt();
			
			// 참조변수에 알맞은 객체 대입
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@";
			String ip = "localhost";
			String port = ":1521";
			String sid = ":XE";
			String user = "kh_peb";
			String pw = "kh1234";
			
			conn = DriverManager.getConnection(type+ip+port+sid, user,pw);
			
			String sql =   "SELECT EMP_NAME, JOB_NAME, SALARY, SALARY * 12 AS ANNULINCOME"
					     + " FROM EMPLOYEE"
					     + " JOIN JOB USING(JOB_CODE)"
					     + " WHERE JOB_NAME = '" + input1 + "'"
					     + " AND SALARY > " + input2;
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			// 조회 결과(rs)를 List 옮겨담기
			List<Employee> list = new ArrayList<Employee>();
			
			while(rs.next()) {
				
				// 현재 행에 존재하는 컬럼값 얻어오기
				String empName = rs.getString("EMP_NAME");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				int annulincome = rs.getInt("ANNULINCOME");
				
				// Employee 객체를 생성하여 컬럼값 담기
				Employee employee = new Employee(empName, jobName, salary, annulincome);
				
				//생성된 Emp 객체를 List에 추가
				list.add(employee);
				
			}
			
			// 만약에 List에 추가되면 Emp 객체가 없다면 "조회 결과 없음" 있다면 순차적으로 출력
			if(list.isEmpty()) {
				System.out.println("조회 결과 없음");
			} else {
				
				// 향상된 for문
				for( Employee employee : list ) {
					System.out.println(employee);
				}
				
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			try {
				
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
			} catch(Exception e) {
				
				e.printStackTrace();
				
			}
			
		}
		
	}
}
