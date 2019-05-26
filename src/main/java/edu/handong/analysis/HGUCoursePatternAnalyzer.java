package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		String dataPath = args[0]; // csv file to be analyzed
		String resultPath = args[1]; // the file path where the results are saved.
		ArrayList<String> lines = Utils.getLines(dataPath, true);
		
		students = loadStudentCourseRecords(lines);
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
		
		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, resultPath);
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file.
	 * Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {
		
		// TODO: Implement this method
		//course 생성 
		
		students = new HashMap<String, Student>();
		Course aCourse = new Course(lines.get(0));
		
		Student student1 = new Student(aCourse.getStudentId());
		
		for(String line : lines) {
			
			aCourse = new Course(line); //처음 비교할 게 없을 때에는 생성 // 첫번째 꺼만 받아오는함수 .get(0)
			
			if(student1.getStudentId().equals(aCourse.getStudentId())){
				
				student1.addCourse(aCourse);
				//(String에는 StudentId) (Student에는 Student instance들이 들어간다.)
				students.put(student1.getStudentId(),student1); 
				
			}else {
				student1 = new Student(aCourse.getStudentId());
				student1.addCourse(aCourse);
			}
			
			
		}
				
		return students;
	}
	
	

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		
		// TODO: Implement this method
				
		ArrayList <String> fin = new ArrayList<String>(); 
		
		String first , last;
		fin.add("StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester");
		
		for (Student sortfile :sortedStudents.values()) {
			first = sortfile.getStudentId();
			first += "," + Integer.toString(sortfile.getSemestersByYearAndSemester().size());
			
			for(int i=1 ; i<= sortfile.getSemestersByYearAndSemester().size();i++) {
				last = ",";
				last += Integer.toString(i);
				last += ",";
				last += Integer.toString(sortfile.getNumCourseInNthSemester(i));
				fin.add(first+last);
			
			}

		}


		
		return fin; // do not forget to return a proper variable.
	}
}
