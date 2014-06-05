package feature;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import libsvm_interface.SVMFeature;
import data.AllData;

public class MakeFeature {
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("Usage: [feature name]");
			System.exit(1);
		}

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

		AllData data = AllData.getInstance(null, "projects,resources,essays");

		String[] feature_names = args[0].split(",");
		for (String feature_name : feature_names) {
			LinkedList<Integer> train_pid = new LinkedList<Integer>();
			LinkedList<Integer> test_pid = new LinkedList<Integer>();

			BufferedReader reader = new BufferedReader(new FileReader(
					"train.txt"));
			temp = null;
			while ((temp = reader.readLine()) != null) {
				String[] arr = temp.split(" ");
				int newid = Integer.parseInt(arr[1]);
				train_pid.add(newid);
			}
			reader.close();
			reader = new BufferedReader(new FileReader("test.txt"));
			temp = null;
			while ((temp = reader.readLine()) != null) {
				String[] arr = temp.split(" ");
				int newid = Integer.parseInt(arr[1]);
				test_pid.add(newid);
			}
			reader.close();

			LinkedList<SVMFeature> train_feature = new LinkedList<SVMFeature>();
			LinkedList<SVMFeature> test_feature = new LinkedList<SVMFeature>();

			FeatureList featurelist = null;

			// switch
			if (feature_name.equals("yep_EssayLength")) {
				featurelist = new EssayLength();
			}
			if (feature_name.equals("yep_StudentReached")) {
				featurelist = new StudentReached();
			}
			if (feature_name.equals("yep_Price")) {
				featurelist = new Price();
			}
			if (feature_name.equals("yep_PriceSquare")) {
				featurelist = new PriceSquare();
			}
			if (feature_name.equals("yep_PovertyLevel")) {
				featurelist = new PovertyLevel();
			}
			if (feature_name.equals("yep_GradeLevel")) {
				featurelist = new GradeLevel();
			} 
			if (feature_name.equals("yep_PostDate")) {
				featurelist = new PostDate();
			} 
			if (feature_name.equals("yep_Fulfillment")) {
				featurelist = new Fulfillment();
			} 
			if (feature_name.equals("yep_MaxUnitPrize")) {
				featurelist = new MaxUnitPrize();
			} 

			featurelist.train_pid = train_pid;
			featurelist.test_pid = test_pid;

			featurelist.map = map;
			featurelist.data = data;

			featurelist.getFeature(train_feature, test_feature);

			int feature_num = featurelist.feature_num;
			
			Iterator<SVMFeature> iter = null;
			for (int i=0; i<feature_num; i++){
				// scale
				double mean = 0;
				iter = train_feature.iterator();
				while (iter.hasNext()) {
					SVMFeature f = iter.next();
					if (f.fid != i) continue;
					mean += f.fv;
				}
				mean /= train_feature.size();
	
				double var = 0;
				iter = train_feature.iterator();
				while (iter.hasNext()) {
					SVMFeature f = iter.next();
					if (f.fid != i) continue;
					double v = f.fv;
					var += (v - mean) * (v - mean);
				}
				var = var / train_feature.size();
				System.out.println("feature_name: "+feature_name+"\tfid: "+i+"\tmean: " + mean + "\tvar: " + var);
	
				
				iter = train_feature.iterator();
				while (iter.hasNext()) {
					SVMFeature f = iter.next();
					if (f.fid != i) continue;
					f.fv = (f.fv - mean) / Math.sqrt(var);
				}
				iter = test_feature.iterator();
				while (iter.hasNext()) {
					SVMFeature f = iter.next();
					if (f.fid != i) continue;
					f.fv = (f.fv - mean) / Math.sqrt(var);
				}
	
			}
			
			PrintStream outp = new PrintStream("features/train.txt."
					+ feature_name);
			outp.println(feature_num);
			iter = train_feature.iterator();
			while (iter.hasNext()) {
				SVMFeature f = iter.next();
				outp.println("1 "+f.fid+":"+f.fv);
			}
			outp.close();
			
			outp = new PrintStream("features/test.txt." + feature_name);
			outp.println(feature_num);
			iter = test_feature.iterator();
			while (iter.hasNext()) {
				SVMFeature f = iter.next();
				outp.println("1 "+f.fid+":"+f.fv);
			}
			outp.close();
		}
	}
}
