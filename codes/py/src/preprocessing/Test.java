package preprocessing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

public class Test {
	private static final String inpath = "D:\\Users\\panye\\kddcup2014\\projects.csv\\projects.csv";
	
	public static void main(String[] args) throws IOException{
		FileInputStream f = new FileInputStream(inpath);
		BufferedReader in = new BufferedReader(new InputStreamReader(f));
		String s = in.readLine();
		String[] temp = s.split(",");
		s = in.readLine();
		ArrayList<String> temp2 = CSVFileUtil.fromCSVLinetoArray(s);
		for (int i = 0; i < temp2.size(); i++)
			System.out.println(temp[i]+" "+temp2.get(i));
		int max = 0;
		Hashtable<String, Integer> h = new Hashtable<String, Integer>();
		String type ="asdfa";
		while (s != null){
			ArrayList<String> splits = CSVFileUtil.fromCSVLinetoArray(s);
			//System.out.println(splits.get(25));
			if (h.get(splits.get(25)) == null) h.put(splits.get(25), 1);
			else{
				h.put(splits.get(25), h.get(splits.get(25))+1);
				if (max < h.get(splits.get(25))){
					max = h.get(splits.get(25));
					type = splits.get(25);
				}
			}
			
			s = in.readLine();
		}
		System.out.println(max+" "+type);
		in.close();
	}
}
