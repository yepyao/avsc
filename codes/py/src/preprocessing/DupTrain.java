package preprocessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class DupTrain {
	private static final String inpath = "D:\\Users\\panye\\kddcup2014\\train.txt";
	private static final String outpath = "D:\\Users\\panye\\kddcup2014\\train2.txt";
	private static final double splitratio = 0.2;
	private static final int repeatTime = 2;
	
	
	public static void main(String[] args) throws IOException{
		FileInputStream f = new FileInputStream(inpath);
		BufferedReader in = new BufferedReader(new InputStreamReader(f));
		String s = in.readLine();
		int line = 0;
		while (s != null){
			line++;
			s = in.readLine();
		}
		in.close();
		
		f = new FileInputStream(inpath);
		in = new BufferedReader(new InputStreamReader(f));
		FileOutputStream f2 = new FileOutputStream(outpath);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		int bound  = (int)(line * splitratio);
		int index = 0;
		while (s != null){
			String[] temp = s.split(" ");
			index++;
			if (index < bound && temp[2].equals("1")){
				for (int i = 0; i < repeatTime; i++)
					out.write(s+"\n");
			}
			out.write(s+"\n");
			s = in.readLine();
		}
		in.close();
		out.close();
	}
}
