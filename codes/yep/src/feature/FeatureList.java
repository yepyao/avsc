package feature;

import java.util.ArrayList;
import java.util.LinkedList;

import libsvm_interface.SVMFeature;
import data.AllData;

public abstract class FeatureList {
	public ArrayList<String> map = null;
	public AllData data = null;
	
	public int feature_num = 0;
	
	public LinkedList<Integer> train_pid = null;
	public LinkedList<Integer> test_pid = null;
	
	public abstract void getFeature(LinkedList<SVMFeature> train, LinkedList<SVMFeature> test);
}
