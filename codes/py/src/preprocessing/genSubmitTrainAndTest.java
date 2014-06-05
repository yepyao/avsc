package preprocessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Hashtable;

public class genSubmitTrainAndTest {
	private static final String inpath = "D:\\Users\\panye\\kddcup2014\\train";
	private static final String inpath2 = "D:\\Users\\panye\\kddcup2014\\test";
	private static final String inpath3 = "D:\\Users\\panye\\kddcup2014\\projectIDMapping";
	private static final String inpath4 = "D:\\Users\\panye\\kddcup2014\\outcomes.csv\\outcomes.csv";
	private static final String outpath = "D:\\Users\\panye\\kddcup2014\\train.txt";
	private static final String outpath2 = "D:\\Users\\panye\\kddcup2014\\test.txt";
	
	public static void main(String[] args) throws IOException{
		FileInputStream f = new FileInputStream(inpath3);
		BufferedReader in = new BufferedReader(new InputStreamReader(f));
		String s = in.readLine();
		Hashtable<String, String> id = new Hashtable<String, String>();
		while (s != null){
			String[] temp = s.split(" ");
			id.put(temp[0], temp[1]);
			s = in.readLine();
		}
		in.close();
		
		f = new FileInputStream(inpath4);
		in = new BufferedReader(new InputStreamReader(f));
		s = in.readLine();
		s = in.readLine();
		Hashtable<String, Integer> posH = new Hashtable<String, Integer>();
		while (s != null){
			ArrayList<String> splits = CSVFileUtil.fromCSVLinetoArray(s);
			if (splits.get(1).equals("t")) 	posH.put(splits.get(0), 1);
			s = in.readLine();
		}
		in.close();
		
		
		f = new FileInputStream(inpath);
		in = new BufferedReader(new InputStreamReader(f));
		FileOutputStream f2 = new FileOutputStream(outpath);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		while (s != null){
			ArrayList<String> splits = CSVFileUtil.fromCSVLinetoArray(s);
			String user = "0";
			String item = id.get(splits.get(0));
			String rate = "0";
			if (posH.get(splits.get(0)) != null) rate = "1";
			out.write(user+" "+item+" "+rate+"\n");
			s = in.readLine();
		}
		in.close();
		out.close();
		
		f = new FileInputStream(inpath2);
		in = new BufferedReader(new InputStreamReader(f));
		f2 = new FileOutputStream(outpath2);
		out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		while (s != null){
			ArrayList<String> splits = CSVFileUtil.fromCSVLinetoArray(s);
			String user = "0";
			String item = id.get(splits.get(0));
			String rate = "0";
			out.write(user+" "+item+" "+rate+"\n");
			s = in.readLine();
		}
		in.close();
		out.close();
	}
}
