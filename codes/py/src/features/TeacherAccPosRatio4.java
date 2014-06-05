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

public class TeacherAccPosRatio4 {
	public static void main(String[] args) throws IOException{
		FileInputStream f = new FileInputStream(args[1]); //mapping
		BufferedReader in = new BufferedReader(new InputStreamReader(f));
		Hashtable<String, String> id = new Hashtable<String, String>();
		String s = in.readLine();
		while (s != null){
			String[] temp = s.split(" ");
			id.put(temp[1], temp[0]);
			s = in.readLine();
		}
		in.close();
		
		f = new FileInputStream(args[3]); //train.txt
		in = new BufferedReader(new InputStreamReader(f));
		Hashtable<String, Integer> train = new Hashtable<String, Integer>();
		s = in.readLine();
		while (s != null){
			String[] temp = s.split(" ");
			train.put(id.get(temp[1]), 1);
			s = in.readLine();
		}
		in.close();
		
		f = new FileInputStream(args[0]); // outcomes.csv
		in = new BufferedReader(new InputStreamReader(f));
		s = in.readLine();
		s = in.readLine();
		Hashtable<String, Integer> posH = new Hashtable<String, Integer>();
		while (s != null){
			ArrayList<String> splits = CSVFileUtil.fromCSVLinetoArray(s);
			if (splits.get(1).equals("t") && train.get(splits.get(0)) != null) 	posH.put(splits.get(0), 1);
			s = in.readLine();
		}
		in.close();
		
		
		
		f = new FileInputStream(args[2]); //projects.csv
		in = new BufferedReader(new InputStreamReader(f));
		Hashtable<String, Integer> teacher = new Hashtable<String, Integer>();
		Hashtable<String, Integer> teacherAll = new Hashtable<String, Integer>();
		Hashtable<String, String> projectTeacher = new Hashtable<String, String>();
		s  = in.readLine();
		s = in.readLine();
		int field = Integer.valueOf(args[7]);
		while (s != null){
			ArrayList<String> splits = CSVFileUtil.fromCSVLinetoArray(s);
			projectTeacher.put(splits.get(0), splits.get(field));
			if (train.get(splits.get(0)) != null && splits.size() > 33){
				if (posH.get(splits.get(0)) != null){
					if (teacher.get(splits.get(field)) == null) teacher.put(splits.get(field), 1);
					else teacher.put(splits.get(field), teacher.get(splits.get(field))+1);
				}
				if (teacherAll.get(splits.get(field)) == null) teacherAll.put(splits.get(field), 1);
				else teacherAll.put(splits.get(field), teacherAll.get(splits.get(field))+1);
			}
			s = in.readLine();
		}
		in.close();
		
		f = new FileInputStream(args[3]); //train.txt
		in = new BufferedReader(new InputStreamReader(f));
		FileOutputStream f2 = new FileOutputStream(args[4]);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		out.write("3"+"\n");
		while (s != null){
			String[] temp = s.split(" ");
			int one = 0;
			if (teacher.get(projectTeacher.get(id.get(temp[1]))) != null){
				one = teacher.get(projectTeacher.get(id.get(temp[1])));
				if (temp[2].equals("1")) one--;
			}
			int two = 0;
			if (teacherAll.get(projectTeacher.get(id.get(temp[1]))) != null){
				two = teacherAll.get(projectTeacher.get(id.get(temp[1])));
				two--;
			}
			double three = 0;
			if (two != 0) three = (double) one / two; 
			out.write("3 "+"0:"+String.valueOf(one)+" 1:"+String.valueOf(two)+" 2:"+String.valueOf(three)+"\n");
	//		out.write("2 "+"0:"+String.valueOf(one)+" 1:"+String.valueOf(two)+"\n");
			s = in.readLine();
		}
		in.close();
		out.close();
		
		f = new FileInputStream(args[5]); //test.txt
		in = new BufferedReader(new InputStreamReader(f));
		f2 = new FileOutputStream(args[6]);
		out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		out.write("3"+"\n");
		while (s != null){
			String[] temp = s.split(" ");
			int one = 0;
			if (teacher.get(projectTeacher.get(id.get(temp[1]))) != null){
				one = teacher.get(projectTeacher.get(id.get(temp[1])));
			}
			int two = 0;
			if (teacherAll.get(projectTeacher.get(id.get(temp[1]))) != null){
				two = teacherAll.get(projectTeacher.get(id.get(temp[1])));
			}
			double three = 0;
			if (two != 0) three = (double) one / two; 
			out.write("3 "+"0:"+String.valueOf(one)+" 1:"+String.valueOf(two)+" 2:"+String.valueOf(three)+"\n");
	//		out.write("2 "+"0:"+String.valueOf(one)+" 1:"+String.valueOf(two)+"\n");
			s = in.readLine();
		}
		in.close();
		out.close();
	}
}

