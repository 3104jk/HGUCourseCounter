package edu.handong.analysis.utils;

import java.util.ArrayList;
import java.io.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;



public class Utils {

	public static ArrayList<CSVRecord> getLines(String file,boolean removeHeader) {
		//file에 data path가 저장되어 있음

		ArrayList<CSVRecord> lines = new ArrayList<CSVRecord>();

		try {
			CSVParser parser = new CSVParser(new FileReader(file), CSVFormat.DEFAULT.withHeader()); 
			
			for(CSVRecord record : parser) { 
				lines.add(record);
				
			}
			if(removeHeader == true)
				lines.remove(0);

			parser.close();

		}catch (FileNotFoundException e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);

		}catch(IOException e){
			System.out.println(e);
		}

		return lines;
	}


	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		//파일이 저장이 되게 해야함 file io를 이용해서
		// 디렉토리 만들어주는 거 method directory라는 method 가 있음
		//ArrayList line을 어디서 사용하지?
		PrintWriter pw = null;

		try {
			
			File result = new File(targetFileName);// result path

			if(!result.exists()) {
				result.getParentFile().mkdirs();//존재하지 않으면 mkdir
			}
			pw = new PrintWriter(result);

		} catch(FileNotFoundException e) {
			System.out.println("Error opening the file " + targetFileName);
			System.exit(0);
		}

		for(String line: lines) {
			pw.println(line);
		}

		pw.close();
	}




}
