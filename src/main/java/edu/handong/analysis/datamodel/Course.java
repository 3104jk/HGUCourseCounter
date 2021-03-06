package edu.handong.analysis.datamodel;
import org.apache.commons.csv.CSVRecord;

public class Course { //파일을 읽어서 저장
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;

	public Course (CSVRecord line) {
		this.studentId = line.get(0).trim();
		this.yearMonthGraduated= line.get(1).trim();
		this.firstMajor = line.get(2).trim();
		this.secondMajor = line.get(3).trim();
		this.courseCode = line.get(4).trim();
		this.courseName = line.get(5).trim();
		this.courseCredit = line.get(6).trim();
		this.yearTaken = Integer.parseInt(line.get(7).trim());
		this.semesterCourseTaken =  Integer.parseInt(line.get(8).trim());	
	}
	
	public String getStudentId() {
		return studentId;	
	}
	public String getYearMonthGraduated() {
		return yearMonthGraduated;
	}
	public String getFirstMajor() {
		return firstMajor;	
	}
	public String getSecondMajor() {
		return secondMajor;	
	}
	public String getCourseCode() {
		return courseCode;	
	}
	public String getCourseName() {
		return courseName;	
	}
	public String getCourseCredit() {
		return courseCredit;	
	}
	
	public int getYear() {
		return yearTaken;
	}
	
	public String getyearTaken() {	//년도 - 학기 // hashmap의 key
		String yearSemester =  Integer.toString(yearTaken) + "-" + Integer.toString(semesterCourseTaken);
	return yearSemester;	
	}	
}
