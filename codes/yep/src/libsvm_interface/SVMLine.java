package libsvm_interface;

import java.util.LinkedList;

public class SVMLine {
	public int label;
	public LinkedList<SVMFeature> features = new LinkedList<SVMFeature>();
	public SVMLine(int label){
		this.label = label;
	}
}
