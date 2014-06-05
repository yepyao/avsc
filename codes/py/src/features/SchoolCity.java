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

public class SchoolCity {
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
		Hashtable<String, Integer> SchoolCity = new Hashtable<String, Integer>();
		Hashtable<String, String> projectSchoolCity = new Hashtable<String, String>();
		int SchoolCityNum = 0;
		s  = in.readLine();
		s = in.readLine();
		while (s != null){
			ArrayList<String> splits = CSVFileUtil.fromCSVLinetoArray(s);
			projectSchoolCity.put(splits.get(0), splits.get(6));
			if (splits.size() > 33){
				if (SchoolCity.get(splits.get(6)) == null){
					SchoolCity.put(splits.get(6), SchoolCityNum);
					SchoolCityNum++;
				}
			}
			s = in.readLine();
		}
		in.close();
		System.out.println(SchoolCityNum);
		
		f = new FileInputStream(args[2]); //train.txt
		in = new BufferedReader(new InputStreamReader(f));
		FileOutputStream f2 = new FileOutputStream(args[3]);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		out.write(String.valueOf(SchoolCityNum)+"\n");
		while (s != null){
			String[] temp = s.split(" ");
			out.write("1 "+SchoolCity.get(projectSchoolCity.get(id.get(temp[1])))+":1"+"\n");
			s = in.readLine();
		}
		in.close();
		out.close();
		
		f = new FileInputStream(args[4]); //test.txt
		in = new BufferedReader(new InputStreamReader(f));
		f2 = new FileOutputStream(args[5]);
		out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		out.write(String.valueOf(SchoolCityNum)+"\n");
		while (s != null){
			String[] temp = s.split(" ");
			out.write("1 "+SchoolCity.get(projectSchoolCity.get(id.get(temp[1])))+":1"+"\n");
			s = in.readLine();
		}
		in.close();
		out.close();
	}
}

