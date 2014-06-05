package feature;

import java.util.ArrayList;
import java.util.LinkedList;

import data.AllData;

public abstract class Feature {
	public ArrayList<String> map = null;
	public AllData data = null;
	
	public LinkedList<Integer> train_pid = null;
	public LinkedList<Integer> test_pid = null;
	
	public abstract void getFeature(LinkedList<Double> train, LinkedList<Double> test);
}
