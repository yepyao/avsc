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

public class MonthCount {
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
		int trainSum = 0;
		s = in.readLine();
		while (s != null){
			trainSum++;
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
		Hashtable<String, Integer> teacher2 = new Hashtable<String, Integer>();
		Hashtable<String, Integer> teacherAll2 = new Hashtable<String, Integer>();
		Hashtable<String, String> projectTeacher = new Hashtable<String, String>();
		s  = in.readLine();
		s = in.readLine();
		int field = Integer.valueOf(args[7]);
		int index = 0;
		while (s != null){
			ArrayList<String> splits = CSVFileUtil.fromCSVLinetoArray(s);
			String month = splits.get(field).substring(5,7);
			projectTeacher.put(splits.get(0), month);
			if (train.get(splits.get(0)) != null && splits.size() > 33){
				index++;
				if (index < trainSum / 2){
					if (posH.get(splits.get(0)) != null){
						if (teacher.get(month) == null) teacher.put(month, 1);
						else teacher.put(month, teacher.get(month)+1);
					}
					if (teacherAll.get(month) == null) teacherAll.put(month, 1);
					else teacherAll.put(month, teacherAll.get(month)+1);
				}else{
					if (posH.get(splits.get(0)) != null){
						if (teacher2.get(month) == null) teacher2.put(month, 1);
						else teacher2.put(month, teacher2.get(month)+1);
					}
					if (teacherAll2.get(month) == null) teacherAll2.put(month, 1);
					else teacherAll2.put(month, teacherAll2.get(month)+1);
				}
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
		index = 0;
		while (s != null){
			String[] temp = s.split(" ");
			int one = 0;
			int two = 0;
			double three = 0;
			index++;
			if (index < trainSum / 2){
				if (teacher2.get(projectTeacher.get(id.get(temp[1]))) != null)
					one = teacher2.get(projectTeacher.get(id.get(temp[1])));
				if (teacherAll2.get(projectTeacher.get(id.get(temp[1]))) != null)
					two = teacherAll2.get(projectTeacher.get(id.get(temp[1])));
				if (two != 0) three = (double) one / two;
			}else{
				if (teacher.get(projectTeacher.get(id.get(temp[1]))) != null)
					one = teacher.get(projectTeacher.get(id.get(temp[1])));
				if (teacherAll.get(projectTeacher.get(id.get(temp[1]))) != null)
					two = teacherAll.get(projectTeacher.get(id.get(temp[1])));
				if (two != 0) three = (double) one / two;
			}
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
			double one = 0;
			if (teacher.get(projectTeacher.get(id.get(temp[1]))) != null){
				one += teacher.get(projectTeacher.get(id.get(temp[1])));
			}
			if (teacher2.get(projectTeacher.get(id.get(temp[1]))) != null){
				one += teacher2.get(projectTeacher.get(id.get(temp[1])));
			}
			double two = 0;
			if (teacherAll.get(projectTeacher.get(id.get(temp[1]))) != null){
				two += teacherAll.get(projectTeacher.get(id.get(temp[1])));
			}
			if (teacherAll2.get(projectTeacher.get(id.get(temp[1]))) != null){
				two += teacherAll2.get(projectTeacher.get(id.get(temp[1])));
			}
			one = one / 2;
			two = two / 2;
			double three = 0;
			if (two != 0) three = (double) one / two; 
			out.write("3 "+"0:"+String.valueOf((int)one)+" 1:"+String.valueOf((int)two)+" 2:"+String.valueOf(three)+"\n");
	//		out.write("2 "+"0:"+String.valueOf(one)+" 1:"+String.valueOf(two)+"\n");
			s = in.readLine();
		}
		in.close();
		out.close();
	}
}

