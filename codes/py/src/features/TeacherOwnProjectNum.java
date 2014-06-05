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

public class TeacherOwnProjectNum {
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
		Hashtable<String, String> pro2tea = new Hashtable<String, String>();
		s  = in.readLine();
		while (s != null){
			ArrayList<String> splits = CSVFileUtil.fromCSVLinetoArray(s);
			if (teacher.get(splits.get(1)) == null) teacher.put(splits.get(1), 1);
			else teacher.put(splits.get(1), teacher.get(splits.get(1))+1);
			pro2tea.put(splits.get(0), splits.get(1));
			s = in.readLine();
		}
		in.close();
		
		f = new FileInputStream(args[2]); //train.txt
		in = new BufferedReader(new InputStreamReader(f));
		FileOutputStream f2 = new FileOutputStream(args[3]);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		out.write("1"+"\n");
		double avg = 0;
		double deviation = 0;
		int n = 0;
		ArrayList<Integer> num = new ArrayList<Integer>();
		while (s != null){
			n++;
			String[] temp = s.split(" ");
			int time = 0;
			if (teacher.get(pro2tea.get(id.get(temp[1]))) != null) time = teacher.get(pro2tea.get(id.get(temp[1])));
			num.add(time);
			avg += time;
			s = in.readLine();
		}
		avg = avg / n;
		for (int i = 0; i < num.size(); i++)
			deviation += (num.get(i) - avg) * (num.get(i) - avg);
		deviation = Math.sqrt(deviation / n);
		for (int i = 0; i < num.size(); i++)
			out.write("1 0:"+String.valueOf((num.get(i)-avg)/deviation)+"\n");
		in.close();
		out.close();
		
		f = new FileInputStream(args[4]); //test.txt
		in = new BufferedReader(new InputStreamReader(f));
		f2 = new FileOutputStream(args[5]);
		out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		out.write("1"+"\n");
		while (s != null){
			String[] temp = s.split(" ");
			Double time = Double.valueOf(0);
			if (teacher.get(pro2tea.get(id.get(temp[1]))) != null) time = Double.valueOf(teacher.get(pro2tea.get(id.get(temp[1]))));
			time = (time - avg) / deviation;
			out.write("1 0:"+String.valueOf(time)+"\n");
			s = in.readLine();
		}
		in.close();
		out.close();
	}
}
