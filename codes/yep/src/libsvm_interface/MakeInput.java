package libsvm_interface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class MakeInput {
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: [conf identify] [output dir]");
			System.exit(1);
		}
		String conf_id = args[0];
		LinkedList<String> tt = new LinkedList<String>();
		tt.add("train");
		tt.add("test");

		for (String t : tt) {
			LinkedList<SVMLine> lines = new LinkedList<SVMLine>();
			BufferedReader reader = new BufferedReader(new FileReader(t
					+ ".txt"));
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				String[] arr = temp.split(" ");
				int label = Integer.parseInt(arr[2]);
				lines.add(new SVMLine(label));
			}
			reader.close();
			System.out.println(t + " lines: " + lines.size());

			BufferedReader conf_reader = new BufferedReader(new FileReader(
					"./T.buf/" + conf_id + ".conf"));
			temp = null;
			int f_count = 0;
			while ((temp = conf_reader.readLine()) != null) {
				if (temp.startsWith("#") || temp.startsWith("-")
						|| temp.equals(""))
					continue;
				String feature_name = temp.trim();

				reader = new BufferedReader(new FileReader("features/" + t
						+ ".txt." + feature_name));
				String feature_line = reader.readLine();
				int feature_num = Integer.parseInt(feature_line);

				Iterator<SVMLine> iter = lines.iterator();
				while (iter.hasNext()) {
					SVMLine line = iter.next();
					feature_line = reader.readLine();
					if (feature_line == null) {
						System.err
								.println("feature line number error: features/"
										+ t + ".txt." + feature_name);
						System.exit(1);
					}
					String[] arr = feature_line.split(" ");
					int size = Integer.parseInt(arr[0]);
					if (arr.length != size + 1) {
						System.err.println("feature format error: features/"
								+ t + ".txt." + feature_name);
						System.exit(1);
					}
					for (int i = 0; i < size; i++) {
						String feature = arr[i + 1];
						String[] nv = feature.split(":");
						int fid = Integer.parseInt(nv[0]) + f_count + 1;
						double fv = Double.parseDouble(nv[1]);
						line.features.add(new SVMFeature(fid, fv));
					}
				}
				reader.close();
				f_count += feature_num;

			}// end of each feature line
			conf_reader.close();

			// begin to output
			PrintStream outp = new PrintStream(args[1] + t + "." + conf_id
					+ ".svm_buffer");
			Iterator<SVMLine> iter = lines.iterator();
			while (iter.hasNext()) {
				SVMLine line = iter.next();
				outp.print(line.label);
				Iterator<SVMFeature> fiter = line.features.iterator();
				while (fiter.hasNext()) {
					SVMFeature feature = fiter.next();
					outp.print(" " + feature.fid + ":" + feature.fv);
				}
				outp.println();
			}
			outp.close();

			outp = new PrintStream(args[1] + t + "." + conf_id
					+ ".svm_buffer.group");
			outp.println(lines.size());
			outp.close();
		}// end for "test" "train"
	}
}