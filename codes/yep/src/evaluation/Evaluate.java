package evaluation;

import java.util.LinkedList;

public class Evaluate {
	public static void main(String[] args) {
		LinkedList<Case> data = new LinkedList<Case>();
		
		Case c;
		c = new Case(0, false);
		data.add(c);
		
		c = new Case(0.5, false);
		data.add(c);
		
		c= new Case(0.5,true);
		data.add(c);
		
		c= new Case(1,true);
		data.add(c);
		
		System.out.println(AUC.calcAUC(data));
	}
}
