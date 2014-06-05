package feature;

import java.util.Iterator;
import java.util.LinkedList;

import data.Essay;
import data.Project;
import libsvm_interface.SVMFeature;

public class PovertyLevel extends FeatureList {

	LinkedList<String> field_map = new LinkedList<String>();
	@Override
	public void getFeature(LinkedList<SVMFeature> train,
			LinkedList<SVMFeature> test) {
		
		Iterator<Project> data_iter = data.projects.values().iterator();
		while (data_iter.hasNext()) {
			Project project = data_iter.next();
			if (!field_map.contains(project.poverty_level))
				field_map.add(project.poverty_level);
		}
		System.out.println("poverty_level num: "+field_map.size());
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
			list.add(new SVMFeature(field_map.indexOf(project.poverty_level), 1));
		}
		
	}

}
