package features;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import preprocessing.CSVFileUtil;

public class TeacherAcc {
	public static void main(String[] args) throws IOException{
		FileInputStream f = new FileInputStream(args[0]); //mapping
		BufferedReader in = new BufferedReader(new InputStreamReader(f));
		Hashtable<String, String> id = new Hashtable<String, String>();
		String s = in.readLine();
		while (s != null){
			String[] temp = s.split(" ");
			id.put(temp[1], temp[0]);
			s = in.readLine();
		}
		in.close();
		
		f = new FileInputStream(args[1]); //projects.csv
		in = new BufferedReader(new InputStreamReader(f));
		Hashtable<String, Integer> teacher = new Hashtable<String, Integer>();
		Hashtable<String, String> projectTeacher = new Hashtable<String, String>();
		int teacherNum = 0;
		s  = in.readLine();
		s = in.readLine();
		while (s != null){
			ArrayList<String> splits = CSVFileUtil.fromCSVLinetoArray(s);
			projectTeacher.put(splits.get(0), splits.get(1));
			if (splits.size() > 33){
				if (teacher.get(splits.get(1)) == null){
					teacher.put(splits.get(1), teacherNum);
					teacherNum++;
				}
			}
			s = in.readLine();
		}
		in.close();
		
		f = new FileInputStream(args[2]); //train.txt
		in = new BufferedReader(new InputStreamReader(f));
		FileOutputStream f2 = new FileOutputStream(args[3]);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		out.write("300000"+"\n");
		while (s != null){
			String[] temp = s.split(" ");
			out.write("1 "+teacher.get(projectTeacher.get(id.get(temp[1])))+":1"+"\n");
			s = in.readLine();
		}
		in.close();
		out.close();
		
		f = new FileInputStream(args[4]); //test.txt
		in = new BufferedReader(new InputStreamReader(f));
		f2 = new FileOutputStream(args[5]);
		out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		out.write("300000"+"\n");
		while (s != null){
			String[] temp = s.split(" ");
			out.write("1 "+teacher.get(projectTeacher.get(id.get(temp[1])))+":1"+"\n");
			s = in.readLine();
		}
		in.close();
		out.close();
	}
}

