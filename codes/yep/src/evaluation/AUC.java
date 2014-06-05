package evaluation;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class AUC {
	public static double calcAUC(LinkedList<Case> data) {
		double auc = 0;
		Collections.sort(data);
		int P = 0, N = 0;
		Iterator<Case> iter = data.iterator();
		while (iter.hasNext()) {
			Case c = iter.next();
			//System.out.println(c.pred);
			if (c.label)
				P++;
			else
				N++;
		}
		//System.out.println("P: " + P + " N: " + N);
		int p = 0, n = 0;
		int pre_p = 0, pre_n = 0;
		double v = Double.MIN_VALUE;

		iter = data.iterator();
		Case c = iter.next();
		while (c != null) {
			pre_p = p;
			pre_n = n;
			v = c.pred;
			while (c.pred >= v) {
				if (c.label)
					p++;
				else
					n++;
				if (!iter.hasNext()) {
					c= null;
					break;
				}
				c = iter.next();
			}
			double temp = pre_p + p;
			temp *= n - pre_n;
			auc += temp;
			
			//System.out.println("auc: "+auc);
		}
		//System.out.println("p: " + p + " n: " + n);
		auc += (p + P) * (N - n);
		return auc / P / N / 2;

	}
}
