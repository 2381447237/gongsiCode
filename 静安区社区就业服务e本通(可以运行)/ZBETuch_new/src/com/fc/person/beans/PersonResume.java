package com.fc.person.beans;

/**
 * 个人简历实体类
 * 
 * @author hxl
 * 
 */
public class PersonResume {
	private String personComputerSkills;// 计算机能力
	private String personComputerZhengshu;// 计算机证书
	private String personForeignLanguages1;// 语种1
	private String personForeignLanguages2;// 语种2
	private String personShunian1;// 熟练程度1
	private String personShunian2;// 熟练程度2
	private String personForeignZhengshu;// 外语证书
	private String personQitazhengshu;// 其他证书
	private String personAssessment;// 自我评价
	private String personFuGangwei1;// 父岗位1
	private String personFuGangwei2;// 父岗位2
	private String personZigangwei1;// 子岗位1
	private String personZigangwei2;// 子岗位2
	private String personGanwei;// 其他岗位
	private String personExpectedSalarylow;// 薪资下限
	private String personExpectedSalaryUp;// 薪资上限
	private String personExpectedSalary;
	private String personNatureEmployment;// 工作性质
	private String personWorktime;// 工作时间
	private String personWorkaddress1;// 工作地点1
	private String personWorkaddress2;// 工作地点2
	private String personWorkaddress3;// 工作地点3
	private String personworkyears;// 工作年限
	private String personSFZ;

	public PersonResume() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonResume(String personComputerSkills,
			String personComputerZhengshu, String personForeignLanguages1,
			String personForeignLanguages2, String personShunian1,
			String personShunian2, String personForeignZhengshu,
			String personQitazhengshu, String personAssessment,
			String personFuGangwei1, String personFuGangwei2,
			String personZigangwei1, String personZigangwei2,
			String personGanwei, String personExpectedSalarylow,
			String personExpectedSalaryUp, String personNatureEmployment,
			String personWorktime, String personWorkaddress1,
			String personWorkaddress2, String personWorkaddress3,
			String personworkyears, String personSFZ) {
		super();
		this.personComputerSkills = personComputerSkills;
		this.personComputerZhengshu = personComputerZhengshu;
		this.personForeignLanguages1 = personForeignLanguages1;
		this.personForeignLanguages2 = personForeignLanguages2;
		this.personShunian1 = personShunian1;
		this.personShunian2 = personShunian2;
		this.personForeignZhengshu = personForeignZhengshu;
		this.personQitazhengshu = personQitazhengshu;
		this.personAssessment = personAssessment;
		this.personFuGangwei1 = personFuGangwei1;
		this.personFuGangwei2 = personFuGangwei2;
		this.personZigangwei1 = personZigangwei1;
		this.personZigangwei2 = personZigangwei2;
		this.personGanwei = personGanwei;
		this.personExpectedSalarylow = personExpectedSalarylow;
		this.personExpectedSalaryUp = personExpectedSalaryUp;
		this.personNatureEmployment = personNatureEmployment;
		this.personWorktime = personWorktime;
		this.personWorkaddress1 = personWorkaddress1;
		this.personWorkaddress2 = personWorkaddress2;
		this.personWorkaddress3 = personWorkaddress3;
		this.personworkyears = personworkyears;
		this.personSFZ = personSFZ;
	}

	public String getPersonExpectedSalary() {
		return personExpectedSalary;
	}

	public void setPersonExpectedSalary(String personExpectedSalary) {
		this.personExpectedSalary = personExpectedSalary;
	}

	public String getPersonForeignZhengshu() {
		return personForeignZhengshu;
	}

	public void setPersonForeignZhengshu(String personForeignZhengshu) {
		this.personForeignZhengshu = personForeignZhengshu;
	}

	public String getPersonSFZ() {
		return personSFZ;
	}

	public void setPersonSFZ(String personSFZ) {
		this.personSFZ = personSFZ;
	}

	public String getPersonComputerSkills() {
		return personComputerSkills;
	}

	public void setPersonComputerSkills(String personComputerSkills) {
		this.personComputerSkills = personComputerSkills;
	}

	public String getPersonComputerZhengshu() {
		return personComputerZhengshu;
	}

	public void setPersonComputerZhengshu(String personComputerZhengshu) {
		this.personComputerZhengshu = personComputerZhengshu;
	}

	public String getPersonForeignLanguages1() {
		return personForeignLanguages1;
	}

	public void setPersonForeignLanguages1(String personForeignLanguages1) {
		this.personForeignLanguages1 = personForeignLanguages1;
	}

	public String getPersonForeignLanguages2() {
		return personForeignLanguages2;
	}

	public void setPersonForeignLanguages2(String personForeignLanguages2) {
		this.personForeignLanguages2 = personForeignLanguages2;
	}

	public String getPersonShunian1() {
		return personShunian1;
	}

	public void setPersonShunian1(String personShunian1) {
		this.personShunian1 = personShunian1;
	}

	public String getPersonShunian2() {
		return personShunian2;
	}

	public void setPersonShunian2(String personShunian2) {
		this.personShunian2 = personShunian2;
	}

	public String getPersonQitazhengshu() {
		return personQitazhengshu;
	}

	public void setPersonQitazhengshu(String personQitazhengshu) {
		this.personQitazhengshu = personQitazhengshu;
	}

	public String getPersonAssessment() {
		return personAssessment;
	}

	public void setPersonAssessment(String personAssessment) {
		this.personAssessment = personAssessment;
	}

	public String getPersonFuGangwei1() {
		return personFuGangwei1;
	}

	public void setPersonFuGangwei1(String personFuGangwei1) {
		this.personFuGangwei1 = personFuGangwei1;
	}

	public String getPersonFuGangwei2() {
		return personFuGangwei2;
	}

	public void setPersonFuGangwei2(String personFuGangwei2) {
		this.personFuGangwei2 = personFuGangwei2;
	}

	public String getPersonZigangwei1() {
		return personZigangwei1;
	}

	public void setPersonZigangwei1(String personZigangwei1) {
		this.personZigangwei1 = personZigangwei1;
	}

	public String getPersonZigangwei2() {
		return personZigangwei2;
	}

	public void setPersonZigangwei2(String personZigangwei2) {
		this.personZigangwei2 = personZigangwei2;
	}

	public String getPersonGanwei() {
		return personGanwei;
	}

	public void setPersonGanwei(String personGanwei) {
		this.personGanwei = personGanwei;
	}

	public String getPersonExpectedSalarylow() {
		return personExpectedSalarylow;
	}

	public void setPersonExpectedSalarylow(String personExpectedSalarylow) {
		this.personExpectedSalarylow = personExpectedSalarylow;
	}

	public String getPersonExpectedSalaryUp() {
		return personExpectedSalaryUp;
	}

	public void setPersonExpectedSalaryUp(String personExpectedSalaryUp) {
		this.personExpectedSalaryUp = personExpectedSalaryUp;
	}

	public String getPersonNatureEmployment() {
		return personNatureEmployment;
	}

	public void setPersonNatureEmployment(String personNatureEmployment) {
		this.personNatureEmployment = personNatureEmployment;
	}

	public String getPersonWorktime() {
		return personWorktime;
	}

	public void setPersonWorktime(String personWorktime) {
		this.personWorktime = personWorktime;
	}

	public String getPersonWorkaddress1() {
		return personWorkaddress1;
	}

	public void setPersonWorkaddress1(String personWorkaddress1) {
		this.personWorkaddress1 = personWorkaddress1;
	}

	public String getPersonWorkaddress2() {
		return personWorkaddress2;
	}

	public void setPersonWorkaddress2(String personWorkaddress2) {
		this.personWorkaddress2 = personWorkaddress2;
	}

	public String getPersonWorkaddress3() {
		return personWorkaddress3;
	}

	public void setPersonWorkaddress3(String personWorkaddress3) {
		this.personWorkaddress3 = personWorkaddress3;
	}

	public String getPersonworkyears() {
		return personworkyears;
	}

	public void setPersonworkyears(String personworkyears) {
		this.personworkyears = personworkyears;
	}

	@Override
	public String toString() {
		return "PersonResume [personComputerSkills=" + personComputerSkills
				+ ", personComputerZhengshu=" + personComputerZhengshu
				+ ", personForeignLanguages1=" + personForeignLanguages1
				+ ", personForeignLanguages2=" + personForeignLanguages2
				+ ", personShunian1=" + personShunian1 + ", personShunian2="
				+ personShunian2 + ", personForeignZhengshu="
				+ personForeignZhengshu + ", personQitazhengshu="
				+ personQitazhengshu + ", personAssessment=" + personAssessment
				+ ", personFuGangwei1=" + personFuGangwei1
				+ ", personFuGangwei2=" + personFuGangwei2
				+ ", personZigangwei1=" + personZigangwei1
				+ ", personZigangwei2=" + personZigangwei2 + ", personGanwei="
				+ personGanwei + ", personExpectedSalarylow="
				+ personExpectedSalarylow + ", personExpectedSalaryUp="
				+ personExpectedSalaryUp + ", personNatureEmployment="
				+ personNatureEmployment + ", personWorktime=" + personWorktime
				+ ", personWorkaddress1=" + personWorkaddress1
				+ ", personWorkaddress2=" + personWorkaddress2
				+ ", personWorkaddress3=" + personWorkaddress3
				+ ", personworkyears=" + personworkyears + ", personSFZ="
				+ personSFZ + "]";
	}

}
