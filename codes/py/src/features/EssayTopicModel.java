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

public class EssayTopicModel {
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
		
		f = new FileInputStream(args[1]); //essayTopicModel
		in = new BufferedReader(new InputStreamReader(f));
		Hashtable<String, String> price = new Hashtable<String, String>();
		s  = in.readLine();
		while (s != null){
			ArrayList<String> splits = CSVFileUtil.fromCSVLinetoArray(s);
			price.put(splits.get(0), s);
			s = in.readLine();
		}
		in.close();
		
		f = new FileInputStream(args[2]); //train.txt
		in = new BufferedReader(new InputStreamReader(f));
		FileOutputStream f2 = new FileOutputStream(args[3]);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		out.write("30"+"\n");
		while (s != null){
			String[] temp = s.split(" ");
			out.write("30");
			if (price.get(id.get(temp[1])) != null){
				ArrayList<String> topic = CSVFileUtil.fromCSVLinetoArray(price.get(id.get(temp[1])));
				for (int i = 0; i < Integer.valueOf(args[6]); i++)
					out.write(" "+String.valueOf(i)+":"+topic.get(i+1));
			}else{
				for (int i = 0; i < Integer.valueOf(args[6]); i++)
					out.write(" "+String.valueOf(i)+":"+String.valueOf(Double.valueOf(1)/30));
			}
			out.write("\n");
			s = in.readLine();
		}
		in.close();
		out.close();
		
		f = new FileInputStream(args[4]); //test.txt
		in = new BufferedReader(new InputStreamReader(f));
		f2 = new FileOutputStream(args[5]);
		out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		out.write("30"+"\n");
		while (s != null){
			String[] temp = s.split(" ");
			out.write("30");
			if (price.get(id.get(temp[1])) != null){
				ArrayList<String> topic = CSVFileUtil.fromCSVLinetoArray(price.get(id.get(temp[1])));
				for (int i = 0; i < Integer.valueOf(args[6]); i++)
					out.write(" "+String.valueOf(i)+":"+topic.get(i+1));
			}else{
				for (int i = 0; i < Integer.valueOf(args[6]); i++)
					out.write(" "+String.valueOf(i)+":"+String.valueOf(Double.valueOf(1)/30));
			}
			out.write("\n");
			s = in.readLine();
		}
		in.close();
		out.close();
	}
}

