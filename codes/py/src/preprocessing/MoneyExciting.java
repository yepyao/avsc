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

public class MoneyExciting {
	private static final String inpath = "D:\\Users\\panye\\kddcup2014\\outcomes.csv\\outcomes.csv";
	private static final String inpath2 = "D:\\Users\\panye\\kddcup2014\\projects.csv\\projects.csv";
	private static final String outpath = "D:\\Users\\panye\\kddcup2014\\moneyExciting.txt";
	private static final String outpath2 = "D:\\Users\\panye\\kddcup2014\\pos.txt";
	
	public static void main(String[] args) throws IOException{
		FileInputStream f = new FileInputStream(inpath);
		BufferedReader in = new BufferedReader(new InputStreamReader(f));
		FileOutputStream f3 = new FileOutputStream(outpath2);
		BufferedWriter out2 = new BufferedWriter(new OutputStreamWriter(f3));
		ArrayList<String> pos = new ArrayList<String>();
		Hashtable<String, Integer> posH = new Hashtable<String, Integer>();
		String s = in.readLine();
		s = in.readLine();
		while (s != null){
			ArrayList<String> splits = CSVFileUtil.fromCSVLinetoArray(s);
			if (splits.get(1).equals("t")){
				pos.add(s);
				posH.put(splits.get(0), 1);
			}
			s = in.readLine();
		}
		in.close();
		
		f = new FileInputStream(inpath2);
		in = new BufferedReader(new InputStreamReader(f));
		FileOutputStream f2 = new FileOutputStream(outpath);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		String[] temp = s.split(",");
		for (int i = 0; i < temp.length; i++)
			System.out.println(i+" "+temp[i]);
		out2.write(s+"\n");
		s = in.readLine();
		while (s != null){
			ArrayList<String> splits = CSVFileUtil.fromCSVLinetoArray(s);
			if (posH.get(splits.get(0))!=null) out2.write(s+"\n");
			int year = Integer.valueOf(splits.get(34).substring(0, 4));
			if (year < 2014){
				int flag = 0;
				if (posH.get(splits.get(0))!=null) flag = 1; 
				out.write(splits.get(30)+" "+String.valueOf(flag)+"\n");
			}
			s = in.readLine();
		}
		in.close();
		out.close();
		out2.close();
	}
}
