package features;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;

import preprocessing.CSVFileUtil;

public class EssayWordPosCount {
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
		int trainSum = 0;
		s = in.readLine();
		while (s != null){
			trainSum++;
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
		Hashtable<String, Integer> hit1 = new Hashtable<String, Integer>();
		Hashtable<String, Integer> show1 = new Hashtable<String, Integer>();
		Hashtable<String, Integer> hit2 = new Hashtable<String, Integer>();
		Hashtable<String, Integer> show2 = new Hashtable<String, Integer>();
		Hashtable<String, String> projectEssay = new Hashtable<String, String>();
		s  = in.readLine();
		s = in.readLine();
		int field = Integer.valueOf(args[7]);
		int index = 0;
		Hashtable<String, Integer> wordTable = new Hashtable<String, Integer>();
		ArrayList<String> totalWords = new ArrayList<String>();
		while (s != null){
			ArrayList<String> splits = CSVFileUtil.fromCSVLinetoArray(s);
			projectEssay.put(splits.get(0), splits.get(field));
			if (train.get(splits.get(0)) != null) index++;
			if (train.get(splits.get(0)) != null && splits.size() > 5){
				String[] words = splits.get(field).split("\\s");
				for (int i = 0; i <words.length; i++){
					String newWord = "";
					words[i] = words[i].toLowerCase();
					for (int j = 0; j < words[i].length(); j++)
						if (words[i].charAt(j) >= 'a' && words[i].charAt(j) <= 'z')
							newWord = newWord + words[i].charAt(j);
					words[i] = newWord;
					if (wordTable.get(words[i]) == null){
						wordTable.put(words[i], 1);
						totalWords.add(words[i]);
					}
				}
				
				Hashtable<String, Integer> occur = new Hashtable<String, Integer>();
				if (index < trainSum / 2){
					if (posH.get(splits.get(0)) != null){
						for (int i = 0; i < words.length; i++)
							if (occur.get(words[i]) == null){
								occur.put(words[i], 1);
								if (hit1.get(words[i]) == null) hit1.put(words[i], 1);
								else hit1.put(words[i], hit1.get(words[i])+1);
							}
					}
					occur = new Hashtable<String, Integer>();
					for (int i = 0; i < words.length; i++)
						if (occur.get(words[i]) == null){
							occur.put(words[i], 1);
							if (show1.get(words[i]) == null) show1.put(words[i], 1);
							else show1.put(words[i], show1.get(words[i])+1);
						}
				}else{
					if (posH.get(splits.get(0)) != null){
						for (int i = 0; i < words.length; i++)
							if (occur.get(words[i]) == null){
								occur.put(words[i], 1);
								if (hit2.get(words[i]) == null) hit2.put(words[i], 1);
								else hit2.put(words[i], hit2.get(words[i])+1);
							}
					}
					occur = new Hashtable<String, Integer>();
					for (int i = 0; i < words.length; i++)
						if (occur.get(words[i]) == null){
							occur.put(words[i], 1);
							if (show2.get(words[i]) == null) show2.put(words[i], 1);
							else show2.put(words[i], show2.get(words[i])+1);
						}
				}
			}
			s = in.readLine();
		}
		in.close();
		
		f = new FileInputStream(args[3]); //train.txt
		in = new BufferedReader(new InputStreamReader(f));
		FileOutputStream f2 = new FileOutputStream(args[4]);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		out.write("1"+"\n");
		index = 0;
		Double threshold  = Double.valueOf(args[8]);
		while (s != null){
			String[] temp = s.split(" ");
			String[] words = projectEssay.get(id.get(temp[1])).split("\\s");
			for (int i = 0; i <words.length; i++){
				String newWord = "";
				words[i] = words[i].toLowerCase();
				for (int j = 0; j < words[i].length(); j++)
					if (words[i].charAt(j) >= 'a' && words[i].charAt(j) <= 'z')
						newWord = newWord + words[i].charAt(j);
				words[i] = newWord;
			}
			index++;
			int num = 0;
			if (index < trainSum / 2){
				for (int i = 0; i < words.length; i++)
					if (hit2.get(words[i]) != null)
						if (Double.valueOf(hit2.get(words[i])) / (double)show2.get(words[i]) > threshold)
							num++;
			}else{
				for (int i = 0; i < words.length; i++)
					if (hit1.get(words[i]) != null)
						if (Double.valueOf(hit1.get(words[i])) / (double)show1.get(words[i]) > threshold)
							num++;
			}
			out.write("1 0:"+String.valueOf((double)num/words.length)+"\n");
			s = in.readLine();
		}
		in.close();
		out.close();
		
		f = new FileInputStream(args[5]); //test.txt
		in = new BufferedReader(new InputStreamReader(f));
		f2 = new FileOutputStream(args[6]);
		out = new BufferedWriter(new OutputStreamWriter(f2));
		s = in.readLine();
		out.write("1"+"\n");
		while (s != null){
			String[] temp = s.split(" ");
			String[] words = projectEssay.get(id.get(temp[1])).split("\\s");
			for (int i = 0; i <words.length; i++){
				String newWord = "";
				words[i] = words[i].toLowerCase();
				for (int j = 0; j < words[i].length(); j++)
					if (words[i].charAt(j) >= 'a' && words[i].charAt(j) <= 'z')
						newWord = newWord + words[i].charAt(j);
				words[i] = newWord;
			}
			int num = 0;
			for (int i = 0; i < words.length; i++){
				double ans1 = 0;
				double ans2 = 0;
				if (hit1.get(words[i]) != null)
					ans1 = Double.valueOf(hit1.get(words[i])) / (double)show1.get(words[i]);
				if (hit2.get(words[i]) != null)
					ans2 = Double.valueOf(hit2.get(words[i])) / (double)show2.get(words[i]);
				if ((ans1 + ans2)/2 > threshold) num++;
			}
			out.write("1 0:"+String.valueOf((double)num/words.length)+"\n");
			s = in.readLine();
		}
		in.close();
		out.close();
		
		f2 = new FileOutputStream(args[9]);
		out = new BufferedWriter(new OutputStreamWriter(f2));
		ArrayList<Tuple> a = new ArrayList<Tuple>();
		for (int i = 0; i < totalWords.size(); i++){
			double ans1 = 0;
			double ans2 = 0;
			if (hit1.get(totalWords.get(i)) != null)
				ans1 = Double.valueOf(hit1.get(totalWords.get(i))) / (double)show1.get(totalWords.get(i));
			if (hit2.get(totalWords.get(i)) != null)
				ans2 = Double.valueOf(hit2.get(totalWords.get(i))) / (double)show2.get(totalWords.get(i));
			if (hit1.get(totalWords.get(i)) != null && hit2.get(totalWords.get(i)) != null && hit1.get(totalWords.get(i)) + hit2.get(totalWords.get(i)) >= 5){
				Tuple temp = new Tuple();
				temp.word = totalWords.get(i);
				temp.hit1 = hit1.get(totalWords.get(i));
				temp.show1 = show1.get(totalWords.get(i));
				temp.ratio1 = temp.hit1 / temp.show1;
				temp.hit2 = hit2.get(totalWords.get(i));
				temp.show2 = show2.get(totalWords.get(i));
				temp.ratio2 = temp.hit2 / temp.show2;
				a.add(temp);
			}
		}
			
		Collections.sort(a, new Comparator<Tuple>() {

			@Override
			public int compare(Tuple o1, Tuple o2) {
				// TODO Auto-generated method stub
				return -new Double(o1.ratio1).compareTo(o2.ratio1);
			}
		});
		for (int i = 0; i < a.size(); i++){
			out.write(a.get(i).word + " "+String.valueOf(a.get(i).hit1) + " " + String.valueOf(a.get(i).show1)+ " "+
					String.valueOf(a.get(i).ratio1)+" ");
			out.write(String.valueOf(a.get(i).hit2) + " " + String.valueOf(a.get(i).show2)+ " "+
					String.valueOf(a.get(i).ratio2)+"\n");
		}
		out.close();
	}
}

