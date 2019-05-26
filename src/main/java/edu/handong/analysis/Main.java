package edu.handong.analysis;

//input = CSV file 
//= 학생들의 정보 수업 강의 정보 학기수 이런 것 등등

//Output = 각 학생이 들은 학기수에 대한 과목 수 

//년도랑 학기 수 번대로 1학기 2학기 3학기 이런식으로 나눔 


public class Main {
	public static void main(String[] args) {
		HGUCoursePatternAnalyzer analyzer = new HGUCoursePatternAnalyzer();
		analyzer.run(args);
	}
}