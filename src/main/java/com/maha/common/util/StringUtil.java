package com.maha.common.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class StringUtil {

	public StringUtil() {
	}

	/**
	 * String이 null인 경우 ""로 바꾸어 준다.
	 * 
	 * @param str
	 * @return
	 */
	public static String nvl(String str) {
		return nvl(str, "");
	}
	
	/**
	 * String이 null인 경우 ""로 바꾸어 준다.
	 * 
	 * @param inStr
	 * @param outStr
	 * @return
	 */
	public static String nvl(String inStr, String outStr) {
		if(inStr == null || "".equals(inStr)) {
			return outStr;
		}
		return inStr;
	}

	/**
	 * String을 숫자로 변환, null인 경우 리턴할 숫자로 바꾸어 준다.
	 * 
	 * @param inStr
	 * @param outNum
	 * @return
	 */
	public static int nvl(String inStr, int outNum) {
		int num = outNum;
		if(inStr != null) {
			num = parseInt(inStr, outNum);
		}
		return num;
	}
	/**
	 * Object가 null인 경우 ""로 바꾸어 준다.
	 * 
	 * @param obj
	 * @return
	 */
	public static String nvl(Object obj) {
		return nvl(obj, "");
	}
	
	/**
	 * Object가 null인 경우 리턴할 문자로 바꾸어 준다.
	 * 
	 * @param obj
	 * @param str
	 * @return
	 */
	public static String nvl(Object obj, String str) {
		if (obj == null || obj.toString().equals("null") || obj.toString().equals("")) {
			return str;
		}
		return obj.toString();
	}
	
	/**
	 * Object가 null인 경우 리턴할 숫자로 바꾸어 준다.
	 * 
	 * @param obj
	 * @param str
	 * @return
	 */
	public static int nvl(Object obj, int num) {
		if(obj != null) {
			return parseInt(obj.toString(), num);
		}
		return num;
	}
	
	/**
	 * String배열 타입 Object를 String배열로 변환.
	 * 
	 * @param obj
	 * @param str
	 * @return
	 */
	public static String[] toArray(Object obj) {
		if(obj == null) {
			return new String[]{""};
		} else if(obj instanceof String[]) {
			return (String[]) obj;
		} else {
			return new String[]{nvl(obj)};
		}
	}

	/**
	 * 문자열(str)이 정해진 바이트(len)를 넘길때 줄임표(tail)를 붙이고 잘라낸다.
	 * 
	 * @param str
	 * @param len
	 * @param tail
	 * @return
	 */
	public static String strCut(String str, int len, String tail) {
		char a;
		int i = 0;
		int realLen = 0;
		
		try {
			if(str == null) return "";
			int srcLen = str.getBytes().length;
			if (srcLen < len) return str;
			String tmpTail = tail;
			if (tail == null) tmpTail = "";
			int tailLen = tmpTail.getBytes().length;
			if (tailLen > len) return "";

			for (i = 0; i < len - tailLen && realLen < len - tailLen; i++) {
				a = str.charAt(i);
				if ((a & 0xFF00) == 0) realLen += 1;
				else realLen += 2;
			}
			while (str.substring(0, i).getBytes().length > len - tailLen) i--;
			return str.substring(0, i) + tmpTail;
		} catch (Exception e) {
			return str;
		}
	}

	/**
	 * 하이픈(-)을 구분자로 사용하는 날짜를 잘라 배열로 리턴한다.
	 * 
	 * @param str
	 * @return
	 */
	public static String[] dateSplit(String str) {
		if(str == null) {
			return new String[] {"", "", ""};
		}
		String arr[] = new String[3];
		String date[] = str.split("-");
		
		arr[0] = date.length <= 0 ? "" : date[0];
		arr[1] = date.length <= 1 ? "" : date[1];
		arr[2] = date.length <= 2 ? "" : date[2];
		
		return arr;
	}
	
	/**
	 * (@)을 구분자로 사용하는 이메일 잘라 배열로 리턴한다.
	 * 
	 * @param str
	 * @return
	 */
	public static String[] emailSplit(String str) {
		if(str == null) {
			return new String[] {"", ""};
		}
		String arr[] = new String[2];
		String email[] = str.split("@");
		
		arr[0] = email.length <= 0 ? "" : email[0];
		arr[1] = email.length <= 1 ? "" : email[1];
		
		return arr;
	}
	
	/**
	 * 문자를 숫자로 변환한다.(실패 시 0을 리턴)
	 * 
	 * @param str
	 * @return
	 */
	public static int parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch(Exception e) {
			return 0;
		}
	}
	
	/**
	 * 문자를 숫자로 변환한다.(변환실패 시 리턴할 숫자로 바꾸어준다.)
	 * 
	 * @param str
	 * @param num
	 * @return
	 */
	public static int parseInt(String str, int num) {
		try {
			return Integer.parseInt(str);
		} catch(Exception e) {
			return num;
		}
	}

	/**
	 * 문자배열을 숫자배열로 변환한다.
	 * 
	 * @param str
	 * @return
	 */
	public static int[] parseInt(String[] str) {
		if(str == null || str.length == 0) {
			return null;
		}
		int[] arrNum = new int[str.length]; 
		for(int i = 0; i < str.length; i++) {
			arrNum[i] = parseInt(str[i]);
		}
		return arrNum;
	}
	
	/**
	 * 정해진 패턴으로 현재 날짜를 리턴한다.
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getToday(String pattern) {
		SimpleDateFormat sdf = null;
		sdf = new SimpleDateFormat(pattern);  
		return sdf.format(new java.util.Date());
	}
	
	/**
	 * 문자형식의 날짜(YYYYMMDD)를 구분자(delimiter)를 추가 하여 변환한다.
	 *  
	 * @param date
	 * @param delimiter
	 * @return
	 */
	public static String getYMD(String date, String delimiter) {
		if(date != null && date.length() >= 8) {
			return date.substring(0, 4) + delimiter +  date.substring(4, 6) + delimiter + date.substring(6, 8);
		} else {
			return date;
		}
	}
	
	/**
	 * 문자형 숫자에 천단위 콤마를 찍어 리턴한다.
	 * 
	 * @param data
	 * @return
	 */
	public static String replaceComma(String data) {
		String formatNum = null;
		if(data != null) {
			int convert = parseInt(data);
			DecimalFormat df = new DecimalFormat("#,###");
			formatNum=(String)df.format(convert);
		}
		return formatNum;
	}
	
	/**
	 * XSS 방지를 위한 특수문자 치환
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceXssStr(String str) {
		if(str != null) {
			str = str.replaceAll("<", "&lt;");
			str = str.replaceAll(">", "&gt;");
			//str = str.replaceAll("#", "&#35;");
			//str = str.replaceAll("&", "&#38;");
			str = str.replaceAll("eval\\((.*)\\)", "");
			str = str.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
			//str = str.replaceAll("script", "");
			str = str.replaceAll("\">", "");
		}
		return str;
	}
	
	/**
	 * 공격 위험성이 존재하는 문자들을 필터링
	 * 
	 * @param str
	 * @return
	 */
	public static String sqlFilter(String str) {
		if(str != null) {
			//str = str.replaceAll("'","''");
			//str = str.replaceAll("\"","\"\"");
			//str = str.replaceAll("\\","\\\\");
			//str = str.replaceAll(";", "");
			str = str.replaceAll("'","&#39;");
			str = str.replaceAll("\"","&quot;");
			str = str.replaceAll("--", "");
			//str = str.replaceAll(" ","");
		}
		return (str);
	}
	
	/**
	 * XSS 방지를 위한 특수문자 치환, 공격 위험성이 존재하는 문자들을 필터링
	 * 
	 * @param str
	 * @return
	 */
	public static String allFilter(String str){
		str = replaceXssStr(str); 
		str = sqlFilter(str);
		return str;
	}

	/**
	 * 위험 특수 문자 치환.
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceHTML(String str) {
		if(str == null) {
			return "";
		} else {
			return str.replaceAll("'", "&#39;").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		}
	}

	/**
	 * 치환된 위험 특수 문자 복원.
	 * 
	 * @param str
	 * @return
	 */
	public static String restoreHTML(String str) {
		if(str == null) {
			return "";
		} else {
			return str.replaceAll("&#39;", "'").replaceAll("&quot;", "\"").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		}
	}
	/**
	 * 치환된 위험 특수 문자 복원 white List.
	 * 
	 * @param str
	 * @return
	 */
	public static String boardHTML(String str) {
		String[] tag = {"&lt;!-- Not Allowed Attribute Filtered --&gt;","&lt;img", "&lt;p&gt;", "&lt;/p&gt;", "&lt;span", "&lt;/span&gt;", "&lt;a", "&lt;/a&gt;", "\"&gt;", "&lt;table&gt;" , "&lt;tr&gt;" , "&lt;td&gt;", "&lt;table" , "&lt;tr" , "&lt;td", "&lt;/table&gt;" , "&lt;/tr&gt;" , "&lt;/td&gt;" ,"&lt;tbody&gt;","&lt;/tbody&gt;","&lt;strong&gt;","&lt;/strong&gt;","&lt;u&gt;","&lt;/u&gt;","&lt;em&gt;","&lt;/em&gt;","&lt;sup&gt;","&lt;/sup&gt;","&lt;sub&gt;","&lt;/sub&gt;","&lt;strike&gt;","&lt;/strike&gt;"};
		String[] restorTag = {"", "<img", "<p>", "</p>", "<span", "</span>", "<a", "</a>", "\">", "<table>" , "<tr>" , "<td>", "<table" , "<tr" , "<td", "</table>" , "</tr>" , "</td>" ,"<tbody>","</tbody>","<strong>","</strong>","<u>","</u>","<em>","</em>","<sup>","</sup>","<sub>","</sub>","<strike>","</strike>" };
		if(str == null) {
			return "";
		} else {
			for(int i=0;i < tag.length;i++){
				str = str.replaceAll(tag[i], restorTag[i]);
			}
			System.out.println("str : "+str);
			return str;
		}
	}
}
