package libsvm_interface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;

import data.AllData;

public class MakeSvmWeight {

	public static void main(String[] args) throws Exception {
		System.exit(1);
		if (args.length != 2) {
			System.err.println("Usage: [conf identify] [output dir]");
			System.exit(1);
		}
		String conf_id = args[0];
		
		ArrayList<String> map = new ArrayList<String>();
		BufferedReader map_reader = new BufferedReader(new FileReader(
				"./data/projectIDMapping"));
		String temp = null;
		int count = 0;
		while ((temp = map_reader.readLine()) != null) {
			String[] arr = temp.split(" ");
			if (arr.length != 2) {
				map_reader.close();
				throw new Exception("field number not equal to 2 in map file!");
			}
			String projectid = arr[0];
			int newid = Integer.parseInt(arr[1]);
			if (newid != count) {
				map_reader.close();
				throw new Exception("newid not continuous!");
			}
			map.add(projectid);
			count++;
		}
		map_reader.close();
		System.out.println("map reading ok: " + count);
		
		AllData data = AllData.getInstance(null, "projects");
		
		BufferedReader reader = new BufferedReader(new FileReader(
				"train.txt"));
		
		PrintStream outp = new PrintStream(args[1] + "train." + conf_id
				+ ".svm_buffer.svm.weight");
		temp = null;
		while ((temp = reader.readLine()) != null) {
			String[] arr = temp.split(" ");
			int newid = Integer.parseInt(arr[1]);
			
			if (Integer.parseInt(arr[2]) == 1)
				outp.println(12);
			else outp.println(1);
		}
		outp.close();
		reader.close();
	}

}
