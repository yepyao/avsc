package feature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import data.Essay;
import data.Resource;
import libsvm_interface.SVMFeature;

public class MaxUnitPrize extends FeatureList {

	@Override
	public void getFeature(LinkedList<SVMFeature> train, LinkedList<SVMFeature> test) {
		this.feature_num = 1;
		Iterator<Integer> iter = train_pid.iterator();
		fillFeature(iter,train);
		
		iter = test_pid.iterator();
		fillFeature(iter,test);
	}

	private void fillFeature(Iterator<Integer> iter, LinkedList<SVMFeature> list) {
		while (iter.hasNext()){
			String projectid = map.get(iter.next());
			ArrayList<Resource> resources = data.project2resourses.get(projectid);
			double max = 0;
			for(int i=0;i<resources.size();i++){
				if (resources.get(i).item_unit_price > max)
					max = resources.get(i).item_unit_price;
			}
			list.add(new SVMFeature(0, max));
		}
		
	}

}
