package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import org.apache.commons.csv.CSVRecord;

public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;

	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */

	String input; //-i
	String output; // -o
	String analysis; //-a 1 / -a 2
	String course;	//-c
	String start; //-s 
	String end; // -e
	boolean help;	//-h	

	public void run(String[] args) {


		Options options = createOptions();

		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}
		}
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		String dataPath = input; // csv file to be analyzed
		String resultPath = output; // the file path where the results are saved.

		ArrayList<CSVRecord> lines = Utils.getLines(dataPath, true);//file read
		students = loadStudentCourseRecords(lines);//student에 라인 넣어주기 (한줄 한줄)	

		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 

		// Generate result lines to be saved.

		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		//ArrayList<String> savedLines = ratioCoursesTakenInEachSemester(sortedStudents);

		// Write a file (named like the value of resultPath) with linesTobeSaved.
		//Utils.writeAFile(linesToBeSaved, resultPath);




		if(analysis.equals("1")){//hw5의 결과 파일 만들기
			//s가 시작한 때부터 - e가 마친 때까지의 년도를 계산해서 출력해라 
			linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
			Utils.writeAFile(linesToBeSaved, resultPath);
		}

		else if(analysis.equals("2")){//새로만들어야 하는 method
			//2 = count per course name and year
			//-c 의 course을 수강한 학생의 비율을 년별로 보여주는 결과가 저장된 파일을 output file로 저장
			//-a 하고 2 가 눌렸을 때 하는 option
			linesToBeSaved = ratioCoursesTakenInEachSemester(sortedStudents);
			Utils.writeAFile(linesToBeSaved, resultPath);
		}



	}
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			input = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o");
			analysis = cmd.getOptionValue("a");
			course= cmd.getOptionValue("c");
			start = cmd.getOptionValue("s");
			end = cmd.getOptionValue("e");
			help = cmd.hasOption("h");


		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}		


	private Options createOptions() {
		Options options = new Options();

		// add options by using OptionBuilder
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("input path")
				.required()
				.build());

		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()
				.argName("output path")
				.required()
				.build());

		// add options by using OptionBuilder
		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year")
				.hasArg()     // this option is intended not to have an option value but just an option
				.argName("Analysis option")
				.required() // this is an optional option. So disabled required().
				.build());

		options.addOption(Option.builder("c").longOpt("course")
				.desc("Course code for '-a 2' option")
				.hasArg()
				.argName("Course code")
				.build());

		options.addOption(Option.builder("s").longOpt("start")
				.desc("Set the start year for analysis e.g., -s 2002")
				.hasArg()
				.argName("Start year for analysis")
				.required()
				.build());

		options.addOption(Option.builder("e").longOpt("end")
				.desc("Set the end year for analysis e.g., -e 2005")
				.hasArg()
				.argName("End year for analysis")
				.required()
				.build());			

		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Help")
				.build());

		return options;
	}

	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer ="";
		formatter.printHelp("HGUCourseCounter", header, options, footer, true);
	}


	/**
	 * This method create HashMap<String,Student> from the data csv file.
	 * Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<CSVRecord> lines) {

		// TODO: Implement this method
		//course 생성 
		int s = Integer.parseInt(start);
		int e = Integer.parseInt(end);

		students = new HashMap<String, Student>();

		Course aCourse = new Course(lines.get(0));

		Student student1 = new Student(lines.get(0).get(0));


		for(CSVRecord line : lines) {

			aCourse = new Course(line); //처음 비교할 게 없을 때에는 생성


			if(student1.getStudentId().equals(aCourse.getStudentId()) && aCourse.getYear() >= s && aCourse.getYear() <= e ){		
				//start year부터 end year까지 add course를 하면 되는데
				student1.addCourse(aCourse);//s의 year을 가진 line부터 add하고 싶다...
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

	private ArrayList<String> ratioCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {

		int s = Integer.parseInt(start);
		int e = Integer.parseInt(end);
		int count , allstudent = 0;

		ArrayList <String> fin = new ArrayList<String>(); 
		String line,c = null;
		fin.add("Year,Semester,CouseCode, CourseName,TotalStudents,StudentsTaken,Rate");

		//			//year //update 되어야 함 2008 -> 2009 		
		//			//학기수		//1학기
		//			
		//			//과목 code	//같아야 함 C 옵션의 과목 code
		//			//과목 이름		//c 옵션의 과목 code의 이름
		//			
		//			//total student //해당년도 해당학기에 있었던 학생들의 수?
		//			//students taken//해당 년도 해당학기에 그 과목을 들은 학생의 수
		//			//ratio			//



		for (int i = s ; i<= e ; i++) {
			for(int j=1 ; j<=4 ; j++) {
				String year= Integer.toString(i) +"-"+ Integer.toString(j);
				count = 0 ;
				allstudent = 0;

				for(Student a : sortedStudents.values()) {
					if(a.year(year)) {
						allstudent++;
						count += a.course(year,course);

						if(c == null) {
							c = a.courseName(course);
						}
					}
				}

				if(count != 0) {
					float ratio = (float)count/(float)allstudent;
					double rateFormat = Math.round(ratio*100); //소수점 1자리
					line = year.split("-")[0] 
							+ "," 
							+ year.split("-")[1] 
							+ "," 
							+ course 
							+ ","  
							+ c 
							+ "," 
							+Integer.toString(count) 
							+ Double.toString(rateFormat)
							+"%";
					fin.add(line);
				}
			}

		}

		return fin; // do not forget to return a proper variable.
	}


}
