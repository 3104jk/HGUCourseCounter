package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;


public class Student {

	private String studentId;
	private ArrayList<Course> coursesTaken; // 학생이 들은 수업 목록	//
	private HashMap<String,Integer> semestersByYearAndSemester; //몇번째 학기인지 정보를 가지고 있는 hash map이 필요함 
	//key : year-semester 
	//eg : 2002-1 이면 1 --> 2002-2 면 2 --> 2003-1이면 3학기 

	public Student (String studentId) {
		this.studentId= studentId;
		this.coursesTaken = new ArrayList<Course>();
		this.semestersByYearAndSemester = new HashMap<String, Integer>();

	}
	public String getStudentId() {
		return studentId;
	}

	public void addCourse(Course newRecord) {
		//line을 읽으면서 생성된 Course instance를  Student instance에 있는 coursesTaken ArrayList에 추가하는 method
		coursesTaken.add(newRecord); //어떤 학생 하나에 소속되어 있음	//그 학생에 대한 coursesTaken 	
		
		if(!(semestersByYearAndSemester.containsKey(newRecord.getyearTaken()))) {
			
			semestersByYearAndSemester.put(newRecord.getyearTaken(),semestersByYearAndSemester.size()+1 );
		}
	
	}

	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		//수강한 년도와 학기 정보를 이용 해당 학생의 순차적인 학기 정보를 저장
		//학기

		return semestersByYearAndSemester;
	}

	public int getNumCourseInNthSemester(int semester) {
		//순차적 학기 번호를 넣으면  (3)
		//해당 학기의 들은 과목의 개수를 돌려줍니다. (3학기에 들었던 과목의 개수)
		//앞의 hashmap에서 3을 입력하면 해당 학생이 2002-1학기에 들은 과목의 개수를 돌려줍니다
		
		int count=0;
		String yearSemester=null;
		
		for(String one : semestersByYearAndSemester.keySet()) {
			if(semestersByYearAndSemester.get(one).equals(semester)) {
				yearSemester = one;
				break;
			}
			
		}
		
		for(Course c : coursesTaken) {
			if(c.getyearTaken().equals(yearSemester)) {
				count++;
			}
		}
		
		return count;
	}
	

}




