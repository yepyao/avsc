package preprocessing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CSVFileUtil {
	// CSV鏂囦欢缂栫爜
	public static final String ENCODE = "UTF-8";

	private FileInputStream fis = null;
	private InputStreamReader isw = null;
	private BufferedReader br = null;

	public CSVFileUtil(String filename) throws Exception {
		fis = new FileInputStream(filename);
		isw = new InputStreamReader(fis, ENCODE);
		br = new BufferedReader(isw);
	}

	// ==========浠ヤ笅鏄叕寮�柟娉�============================
	/**
	 * 浠嶤SV鏂囦欢娴佷腑璇诲彇涓�釜CSV琛屻�
	 * 
	 * @throws Exception
	 */
	public String readLine() throws Exception {

		StringBuffer readLine = new StringBuffer();
		boolean bReadNext = true;

		while (bReadNext) {
			//
			if (readLine.length() > 0) {
				readLine.append("\r\n");
			}
			// 涓�
			String strReadLine = br.readLine();

			// readLine is Null
			if (strReadLine == null) {
				return null;
			}
			readLine.append(strReadLine);

			// 濡傛灉鍙屽紩鍙锋槸濂囨暟鐨勬椂鍊欑户缁鍙栥�鑰冭檻鏈夋崲琛岀殑鏄儏鍐点�
			if (countChar(readLine.toString(), '"', 0) % 2 == 1) {
				bReadNext = true;
			} else {
				bReadNext = false;
			}
		}
		return readLine.toString();
	}

	/**
	 * 鎶奀SV鏂囦欢鐨勪竴琛岃浆鎹㈡垚瀛楃涓叉暟缁勩�鎸囧畾鏁扮粍闀垮害锛屼笉澶熼暱搴︾殑閮ㄥ垎璁剧疆涓簄ull銆�
	 */
	public static String[] fromCSVLine(String source, int size) {
		ArrayList tmpArray = fromCSVLinetoArray(source);
		if (size < tmpArray.size()) {
			size = tmpArray.size();
		}
		String[] rtnArray = new String[size];
		tmpArray.toArray(rtnArray);
		return rtnArray;
	}

	/**
	 * 鎶奀SV鏂囦欢鐨勪竴琛岃浆鎹㈡垚瀛楃涓叉暟缁勩�涓嶆寚瀹氭暟缁勯暱搴︺�
	 */
	public static ArrayList fromCSVLinetoArray(String source) {
		if (source == null || source.length() == 0) {
			return new ArrayList();
		}
		int currentPosition = 0;
		int maxPosition = source.length();
		int nextComma = 0;
		ArrayList rtnArray = new ArrayList();
		while (currentPosition < maxPosition) {
			nextComma = nextComma(source, currentPosition);
			rtnArray.add(nextToken(source, currentPosition, nextComma));
			currentPosition = nextComma + 1;
			if (currentPosition == maxPosition) {
				rtnArray.add("");
			}
		}
		return rtnArray;
	}

	/**
	 * 鎶婂瓧绗︿覆绫诲瀷鐨勬暟缁勮浆鎹㈡垚涓�釜CSV琛屻�锛堣緭鍑篊SV鏂囦欢鐨勬椂鍊欑敤锛�
	 */
	public static String toCSVLine(String[] strArray) {
		if (strArray == null) {
			return "";
		}
		StringBuffer cvsLine = new StringBuffer();
		for (int idx = 0; idx < strArray.length; idx++) {
			String item = addQuote(strArray[idx]);
			cvsLine.append(item);
			if (strArray.length - 1 != idx) {
				cvsLine.append(',');
			}
		}
		return cvsLine.toString();
	}

	/**
	 * 瀛楃涓茬被鍨嬬殑List杞崲鎴愪竴涓狢SV琛屻�锛堣緭鍑篊SV鏂囦欢鐨勬椂鍊欑敤锛�
	 */
	public static String toCSVLine(ArrayList strArrList) {
		if (strArrList == null) {
			return "";
		}
		String[] strArray = new String[strArrList.size()];
		for (int idx = 0; idx < strArrList.size(); idx++) {
			strArray[idx] = (String) strArrList.get(idx);
		}
		return toCSVLine(strArray);
	}

	// ==========浠ヤ笅鏄唴閮ㄤ娇鐢ㄧ殑鏂规硶=============================
	/**
	 * 璁＄畻鎸囧畾鏂囧瓧鐨勪釜鏁般�
	 * 
	 * @param str
	 *            鏂囧瓧鍒�
	 * @param c
	 *            鏂囧瓧
	 * @param start
	 *            寮�浣嶇疆
	 * @return 涓暟
	 */
	private int countChar(String str, char c, int start) {
		int i = 0;
		int index = str.indexOf(c, start);
		return index == -1 ? i : countChar(str, c, index + 1) + 1;
	}

	/**
	 * 鏌ヨ涓嬩竴涓�鍙风殑浣嶇疆銆�
	 * 
	 * @param source
	 *            鏂囧瓧鍒�
	 * @param st
	 *            妫�储寮�浣嶇疆
	 * @return 涓嬩竴涓�鍙风殑浣嶇疆銆�
	 */
	private static int nextComma(String source, int st) {
		int maxPosition = source.length();
		boolean inquote = false;
		while (st < maxPosition) {
			char ch = source.charAt(st);
			if (!inquote && ch == ',') {
				break;
			} else if ('"' == ch) {
				inquote = !inquote;
			}
			st++;
		}
		return st;
	}

	/**
	 * 鍙栧緱涓嬩竴涓瓧绗︿覆
	 */
	private static String nextToken(String source, int st, int nextComma) {
		StringBuffer strb = new StringBuffer();
		int next = st;
		while (next < nextComma) {
			char ch = source.charAt(next++);
			if (ch == '"') {
				if ((st + 1 < next && next < nextComma)
						&& (source.charAt(next) == '"')) {
					strb.append(ch);
					next++;
				}
			} else {
				strb.append(ch);
			}
		}
		return strb.toString();
	}

	/**
	 * 鍦ㄥ瓧绗︿覆鐨勫渚у姞鍙屽紩鍙枫�濡傛灉璇ュ瓧绗︿覆鐨勫唴閮ㄦ湁鍙屽紩鍙风殑璇濓紝鎶�杞崲鎴�"銆�
	 * 
	 * @param item
	 *            瀛楃涓�
	 * @return 澶勭悊杩囩殑瀛楃涓�
	 */
	private static String addQuote(String item) {
		if (item == null || item.length() == 0) {
			return "\"\"";
		}
		StringBuffer sb = new StringBuffer();
		sb.append('"');
		for (int idx = 0; idx < item.length(); idx++) {
			char ch = item.charAt(idx);
			if ('"' == ch) {
				sb.append("\"\"");
			} else {
				sb.append(ch);
			}
		}
		sb.append('"');
		return sb.toString();
	}
}
