package edu.kh.jdbc1.model.vo;

public class Emp { // DB에서 조회된 사원 한명(1행)의 정보를 저장하는 객체

	private String empName;
	private String deptTitle;
	private int salary;
	
	public Emp() {}

	public Emp(String empName, String deptTitle, int salary) {
		super();
		this.empName = empName;
		this.deptTitle = deptTitle;
		this.salary = salary;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDeptTitle() {
		return deptTitle;
	}

	public void setDeptTitle(String deptTitle) {
		this.deptTitle = deptTitle;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "이름 : " + empName + " / 부서명 : " + deptTitle + " / 급여 : " + salary;
	}
	
	
}
