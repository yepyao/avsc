package feature;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import data.Essay;
import data.Project;
import libsvm_interface.SVMFeature;

public class PostDate extends FeatureList {

	@Override
	public void getFeature(LinkedList<SVMFeature> train,
			LinkedList<SVMFeature> test) {
		this.feature_num = 1;
		Iterator<Integer> iter = train_pid.iterator();
		fillFeature(iter, train);

		iter = test_pid.iterator();
		fillFeature(iter, test);
	}

	private void fillFeature(Iterator<Integer> iter, LinkedList<SVMFeature> list) {
		try {
			while (iter.hasNext()) {
				String projectid = map.get(iter.next());
				Project project = data.projects.get(projectid);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = dateFormat.parse(project.date_posted);
				list.add(new SVMFeature(0, date.getTime()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}

}
