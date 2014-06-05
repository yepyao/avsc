package evaluation;

public class Case implements Comparable<Case>{
	double pred;
	boolean label;
	
	public Case(double pred, boolean label){
		this.pred = pred;
		this.label = label;
	}
	
	@Override
	public int compareTo(Case o) {
		return -(new Double(pred).compareTo(o.pred));
	}
}
