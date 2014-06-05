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

public class ResourceTypePos {
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
		Hashtable<String, Integer> type = new Hashtable<String, Integer>();
		Hashtable<String, String> projectType = new Hashtable<String, String>();
		s  = in.readLine();
		s = in.readLine();
		while (s != null){
			ArrayList<String> splits = CSVFileUtil.fromCSVLinetoArray(s);
			projectType.put(splits.get(0), splits.get(25));
			if (splits.size() > 25 && posH.get(splits.get(0)) != null){
				if (type.get(splits.get(25)) == null) type.put(splits.get(25), 1);
				else type.put(splits.get(25), type.get(splits.get(25))+1);
			}
			s = in.readLine();
		}
		in.close();
		
		f = new FileInputStream(args[3]); //train.txt
		in = new BufferedReader(new InputStreamReader(f));
		FileOutputStream f2 = new FileOutputStream(args[4]);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		double avg = 0;
		double deviation = 0;
		int n = 0;
		ArrayList<Integer> num = new ArrayList<Integer>();
		out.write("1"+"\n");
		while (s != null){
			n++;
			String[] temp = s.split(" ");
			int ans = 0;
			if (type.get(projectType.get(id.get(temp[1]))) != null) ans = type.get(projectType.get(id.get(temp[1])));
			num.add(ans);
			avg += ans;
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
		System.out.println(avg+" "+deviation);
		
		f = new FileInputStream(args[5]); //test.txt
		in = new BufferedReader(new InputStreamReader(f));
		f2 = new FileOutputStream(args[6]);
		out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		out.write("1"+"\n");
		while (s != null){
			String[] temp = s.split(" ");
			double ans = 0;
			if (type.get(projectType.get(id.get(temp[1]))) != null) ans = type.get(projectType.get(id.get(temp[1])));
			ans = (ans - avg) /deviation;
			out.write("1 0:"+String.valueOf(ans)+"\n");
			s = in.readLine();
		}
		in.close();
		out.close();
	}
}

