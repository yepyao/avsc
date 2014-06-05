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

public class SchoolNcesid {
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
		Hashtable<String, Integer> schoolNcesid = new Hashtable<String, Integer>();
		Hashtable<String, String> projectschoolNcesid = new Hashtable<String, String>();
		int schoolNcesidNum = 0;
		s  = in.readLine();
		s = in.readLine();
		while (s != null){
			ArrayList<String> splits = CSVFileUtil.fromCSVLinetoArray(s);
			projectschoolNcesid.put(splits.get(0), splits.get(3));
			if (splits.size() > 33){
				if (schoolNcesid.get(splits.get(3)) == null){
					schoolNcesid.put(splits.get(3), schoolNcesidNum);
					schoolNcesidNum++;
				}
			}
			s = in.readLine();
		}
		in.close();
		System.out.println(schoolNcesidNum);
		
		f = new FileInputStream(args[2]); //train.txt
		in = new BufferedReader(new InputStreamReader(f));
		FileOutputStream f2 = new FileOutputStream(args[3]);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		out.write(String.valueOf(schoolNcesidNum)+"\n");
		while (s != null){
			String[] temp = s.split(" ");
			out.write("1 "+schoolNcesid.get(projectschoolNcesid.get(id.get(temp[1])))+":1"+"\n");
			s = in.readLine();
		}
		in.close();
		out.close();
		
		f = new FileInputStream(args[4]); //test.txt
		in = new BufferedReader(new InputStreamReader(f));
		f2 = new FileOutputStream(args[5]);
		out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		out.write(String.valueOf(schoolNcesidNum)+"\n");
		while (s != null){
			String[] temp = s.split(" ");
			out.write("1 "+schoolNcesid.get(projectschoolNcesid.get(id.get(temp[1])))+":1"+"\n");
			s = in.readLine();
		}
		in.close();
		out.close();
	}
}

