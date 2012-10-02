package com.crossdb.sql;

/**
 * SQLite de manera predeterminada no soporta muchas funciones por lo que se
 * capara la posibilidad de usar funciones a las de SQL <b>Not used yet</b>.
 * <p>
 * Will represent a function such as MAX, MIN, OR COUNT.
 * 
 * @author Nelson Efrain A. Cruz - neac03@gmail.com
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.2
 */
// TODO ver si es mejor crear una clase abstracta 
public  abstract class Functions {
	
//	public static final int AVG = 1;
//	public static final int COUNT = 2;
//	public static final int GROUP_CONCAT = 3;
//	public static final int MAX = 4;
//	public static final int MIN = 5;
//	public static final int SUM = 6;
	//TODO clean this class and review doc
	public static final String ABS = "ABS";
	public static final String COALESCE = "COALESCE";
	public static final String IFNULL = "IFNULL";
	public static final String LENGTH = "LENGTH";
	public static final String LIKE = "LIKE";
	public static final String LOWER = "LOWER";
	public static final String LTRIM = "LTRIM";
	public static final String NULLIF = "NULLIF";
	public static final String QUOTE = "QUOTE";
	public static final String REPLACE = "REPLACE";
	public static final String ROUND = "ROUND";
	public static final String RTRIM = "RTRIM";
	public static final String SUBSTR = "SUBSTR";
	public static final String TRIM = "TRIM";
	public static final String UPPER = "UPPER";
	
	
	public static final String AVG = "AVG";
	public static final String COUNT = "COUNT";
	public static final String GROUP_CONCAT = "GROUP_CONCAT";
	public static final String SUM = "SUM";
	public static final String MAX = "MAX";
	public static final String MIN = "MIN";
	
	
	public static final String DATE = "DATE";
	public static final String DATETIME = "DATETIME";
	public static final String TIME = "TIME";

	// public static final int
	// private static Hashtable<Integer, String> mapper = new Hashtable<Integer,
	// String>(6);

//	public static String getFunctionName(int key) {
//		switch (key) {
//		case AVG:
//			return "AVG";
//		case COUNT:
//			return "COUNT";
//		case GROUP_CONCAT:
//			return "GROUO_CONCAT";
//		case MAX:
//			return "MAX";
//		case MIN:
//			return "MIN";
//		case SUM:
//			return "SUM";
//		default:
//			throw new RuntimeException("Illegal argument, not recognized "
//					+ key);
//		}
//	}

}
