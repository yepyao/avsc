package evaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class MakeSubmission {
	public static void main(String[] args) throws Exception {
		if (args.length != 4){
			System.err.println("Usage: [pred] [text] [submission] [projectid map]");
			System.exit(1);
		}
		
		ArrayList<String> map = new ArrayList<String>();
		BufferedReader map_reader = new BufferedReader(new FileReader(args[3]));
		String temp = null;
		int count = 0;
		while((temp = map_reader.readLine())!=null){
			String[] arr = temp.split(" ");
			if (arr.length !=2 ) {
				map_reader.close();
				throw new Exception("field number not equal to 2 in map file!");
			}
			String projectid = arr[0];
			int newid = Integer.parseInt(arr[1]);
			if (newid!=count) {
				map_reader.close();
				throw new Exception("newid not continuous!");
			}
			map.add(projectid);
			count++;
		}
		map_reader.close();
		//System.out.println("map reading ok: "+count);
		
		LinkedList<Case> data = new LinkedList<Case>();
		BufferedReader pred_reader = new BufferedReader(new FileReader(args[0]));
		BufferedReader test_reader = new BufferedReader(new FileReader(args[1]));
		
		PrintStream outp = new PrintStream(args[2]);
		outp.println("projectid,is_exciting");
		
		String pred_line = null;
		String test_line = null;
		while ((pred_line = pred_reader.readLine())!=null){
			test_line = test_reader.readLine();
			if (test_line == null) {
				pred_reader.close();
				test_reader.close();
				throw new Exception("line number not match! (test less)");
			}
			String[] arr = test_line.split(" ");
			if (arr.length!=3) {
				pred_reader.close();
				test_reader.close();
				throw new Exception("field number not equal to 3 in test file!");
			}
			
			double pred_value = Double.parseDouble(pred_line);
			int project_newid = Integer.parseInt(arr[1]);
			boolean label = false;
			if (arr[2].equals("1")) label = true;
			data.add(new Case(pred_value,label));
			outp.println(map.get(project_newid)+","+pred_value);
		}
		if (test_reader.readLine()!=null) {
			pred_reader.close();
			test_reader.close();
			throw new Exception("line number not match!(pred less)");
		}
		pred_reader.close();
		test_reader.close();
		System.out.println("AUC: "+AUC.calcAUC(data));
	}
}
