package data;

import java.util.LinkedList;

public class Helper {
	public static int parseInt(String str){
		return (str.equals(""))? 0 : Integer.parseInt(str);
	}
	
	public static double parseDouble(String str){
		return (str.equals(""))? 0 : Double.parseDouble(str);
	}
	
	public static boolean parseBool(String value) throws Exception{
		if (value.equals("t")) return true;
		else {
			if (!(value.equals("f") || value.equals(""))) {
				throw new Exception("Wrong boolean value.");
			}
			return false;
		}
	}
}
