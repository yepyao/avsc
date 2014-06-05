package data;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class Export {
	static AllData data = null;

	public static void main(String[] args) throws Exception {
		String dir = null;
		if (args.length > 0)
			dir = args[0];
		data = AllData.getInstance(dir, "projects,outcomes");

		PrintStream outp = new PrintStream("model/export.csv");
		PrintStream outp2 = new PrintStream("model/export_ex.csv");

		Iterator<Project> iter = data.projects.values().iterator();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date compare = dateformat.parse("2010-03-15");
		Date point = dateformat.parse("2013-06-30");

		int count_left = 0;
		int count_right = 0;
		int ex_left = 0;
		int ex_right = 0;
		while (iter.hasNext()) {
			Project project = iter.next();
			Date date = dateformat.parse(project.date_posted);
			if (date.getTime() < compare.getTime())
				continue;
			if (!data.outcomes.containsKey(project.projectid))
				continue;
			
			outp.println((date.getTime()-compare.getTime()+0.0)/1000/3600/24/365);
			
			if (date.getTime() < point.getTime())
				count_left++;
			else
				count_right++;

			if (date.getTime() < point.getTime()) {
				if (new_exciting(project)){
					ex_left++;
					outp2.println((date.getTime()-compare.getTime()+0.0)/1000/3600/24/365);
				}
			} else if (data.outcomes.get(project.projectid).is_exciting){
				ex_right++;
				outp2.println((date.getTime()-compare.getTime()+0.0)/1000/3600/24/365);
			}

		}
		System.out.println((double) count_left / count_right);
		System.out.println((double) ex_left / ex_right);
		outp.close();
		outp2.close();
	}

	private static boolean new_exciting(Project project) {
		Outcome outcome = data.outcomes.get(project.projectid);
		if (outcome.is_exciting)
			return true;
		if (outcome.at_least_1_teacher_referred_donor
				&& outcome.fully_funded
				&& outcome.at_least_1_green_donation
				&& (outcome.donation_from_thoughtful_donor
						|| outcome.one_non_teacher_referred_donor_giving_100_plus || outcome.three_or_more_non_teacher_referred_donors)) {
			if (outcome.great_messages_proportion>47) return true;
			else return false;
		} else
			return false;
	}
}
