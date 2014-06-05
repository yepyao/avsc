package feature;

import java.util.Iterator;
import java.util.LinkedList;

import data.Project;
import libsvm_interface.SVMFeature;

public class GradeLevel extends FeatureList {

	LinkedList<String> field_map = new LinkedList<String>();
	@Override
	public void getFeature(LinkedList<SVMFeature> train,
			LinkedList<SVMFeature> test) {
		
		Iterator<Project> data_iter = data.projects.values().iterator();
		while (data_iter.hasNext()) {
			Project project = data_iter.next();
			if (!field_map.contains(project.grade_level))
				field_map.add(project.grade_level);
		}
		System.out.println("grade_level num: "+field_map.size());
		this.feature_num = field_map.size();
		
		Iterator<Integer> iter = train_pid.iterator();
		fillFeature(iter,train);
		
		iter = test_pid.iterator();
		fillFeature(iter,test);

	}
	
	private void fillFeature(Iterator<Integer> iter, LinkedList<SVMFeature> list) {
		while (iter.hasNext()){
			String projectid = map.get(iter.next());
			Project project = data.projects.get(projectid);
			list.add(new SVMFeature(field_map.indexOf(project.grade_level), 1));
		}
		
	}

}
