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

public class ResourceItemNum {
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
		
		f = new FileInputStream(args[1]); //resource.csv
		in = new BufferedReader(new InputStreamReader(f));
		Hashtable<String, Double> resourceNum = new Hashtable<String, Double>();
		s  = in.readLine();
		s = in.readLine();
		while (s != null){
			ArrayList<String> splits = CSVFileUtil.fromCSVLinetoArray(s);
			if (splits.size() > 8 && !splits.get(8).equals(""))
			if (resourceNum.get(splits.get(1)) == null) resourceNum.put(splits.get(1), Double.valueOf(splits.get(8)));
			else resourceNum.put(splits.get(1), resourceNum.get(splits.get(1))+Double.valueOf(splits.get(8)));
			s = in.readLine();
		}
		in.close();
		
		f = new FileInputStream(args[2]); //train.txt
		in = new BufferedReader(new InputStreamReader(f));
		FileOutputStream f2 = new FileOutputStream(args[3]);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		out.write("1"+"\n");
		while (s != null){
			String[] temp = s.split(" ");
			Double ans = Double.valueOf(0);
			if (resourceNum.get(id.get(temp[1])) != null) 
				ans = Double.valueOf(resourceNum.get(id.get(temp[1])));
			s = in.readLine();
			out.write("1 0:"+String.valueOf(ans)+"\n");
		}
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
			Double ans = Double.valueOf(0);
			if (resourceNum.get(id.get(temp[1])) != null) 
				ans = Double.valueOf(resourceNum.get(id.get(temp[1])));
			out.write("1 0:"+String.valueOf(ans)+"\n");
			s = in.readLine();
		}
		in.close();
		out.close();
	}
}

