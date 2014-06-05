package data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

public class Stat2 {
	static AllData data = null;
	
	public static void main(String[] args) throws Exception {

		String dir = null;
		if (args.length > 0)
			dir = args[0];
		data = AllData.getInstance(dir, "projects,outcomes,essays,resources");

		Iterator<Project> iter = data.projects.values().iterator();
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date compare = dateformat.parse("2010-03-15");
		
		LinkedList<Double> flist = new LinkedList<Double>();
		LinkedList<Double> rlist = new LinkedList<Double>();
		
		while (iter.hasNext()) {
			Project project = iter.next();
			Date date = dateformat.parse(project.date_posted);
			if (date.getTime() < compare.getTime()) continue;
			
			if (data.outcomes.containsKey(project.projectid)) {
				flist.add(getString(project));
				rlist.add((double)((data.outcomes.get(project.projectid).is_exciting)?1:0));
			}
		}
		System.out.println("Pearson: " + getPearson(flist,rlist));
	}

	private static double getPearson(LinkedList<Double> flist,
			LinkedList<Double> rlist) {
		if (flist.size()!=rlist.size()) {
			System.err.println("getPearon lenth mot match error!");
			System.exit(1);
		}
		int count = flist.size();
		double fmean=0, rmean=0;
		Iterator<Double> fiter = flist.iterator();
		Iterator<Double> riter = rlist.iterator();
		while (fiter.hasNext()){
			fmean += fiter.next();
			rmean += riter.next();
		}
		fmean /= count;
		rmean /= count;
		
		double fvar = 0, rvar = 0;
		double pc = 0;
		fiter = flist.iterator();
		riter = rlist.iterator();
		while (fiter.hasNext()){
			double fv = fiter.next();
			double rv = riter.next();
			fvar += Math.pow(fv-fmean, 2);
			rvar += Math.pow(rv-rmean, 2);
			pc += (fv-fmean)*(rv-rmean);
		}
		
		fvar /= count;
		rvar /= count;
		
		pc /= count * Math.sqrt(fvar) * Math.sqrt(rvar);
		
		return pc;
	}

	private static Double getString(Project project) {
		return (double)project.students_reached;
		//Essay essay = data.essays.get(project.projectid);
		//return (double)essay.need_statement.length();
		//return (project.teacher_teach_for_america)?"T":"F";
		//ArrayList<Resource> resources = data.project2resourses.get(project.projectid);
		//double max_prize = 0;
		//for(int i=0;i<resources.size();i++)
		//	if (resources.get(i).item_unit_price>max_prize)
		//		max_prize = resources.get(i).item_quantity;
		//return (double)max_prize;
	}

}
