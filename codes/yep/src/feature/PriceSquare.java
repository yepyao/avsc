package feature;

import java.util.Iterator;
import java.util.LinkedList;

import libsvm_interface.SVMFeature;
import data.Project;

public class PriceSquare extends FeatureList {
	@Override
	public void getFeature(LinkedList<SVMFeature> train, LinkedList<SVMFeature> test) {
		this.feature_num = 1;
		Iterator<Integer> iter = train_pid.iterator();
		fillFeature(iter, train);

		iter = test_pid.iterator();
		fillFeature(iter, test);
	}

	private void fillFeature(Iterator<Integer> iter, LinkedList<SVMFeature> list) {
		while (iter.hasNext()) {
			String projectid = map.get(iter.next());
			Project project = data.projects.get(projectid);
			list.add(new SVMFeature(0, Math.pow(project.total_price_including_optional_support,2)));
		}

	}
}
