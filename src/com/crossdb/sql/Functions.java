package com.crossdb.sql;

/**
 * SQLite de manera predeterminada no soporta muchas funciones por lo que se
 * capara la posibilidad de usar funciones a las de SQL <b>Not used yet</b>.
 * <p>
 * Will represent a function such as MAX, MIN, OR COUNT.
 * 
 * 
 * /**
 * <p>
 * Title: crossdb
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Space Program Inc.
 * </p>
 * 
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */

/*
 * import java.util.List; import java.util.Date; import java.sql.ResultSet;
 * import java.sql.Connection; import java.sql.SQLException;
 */
// TODO ver si es mejor crear una clase abstracta 
public class Functions {
	public static final int AVG = 1;
	public static final int COUNT = 2;
	public static final int GROUP_CONCAT = 3;
	public static final int MAX = 4;
	public static final int MIN = 5;
	public static final int SUM = 6;

	// public static final int
	// private static Hashtable<Integer, String> mapper = new Hashtable<Integer,
	// String>(6);

	public static String getFunctionName(int key) {
		switch (key) {
		case AVG:
			return "AVG";
		case COUNT:
			return "COUNT";
		case GROUP_CONCAT:
			return "GROUO_CONCAT";
		case MAX:
			return "MAX";
		case MIN:
			return "MIN";
		case SUM:
			return "SUM";
		default:
			throw new RuntimeException("Illegal argument, not recognized "
					+ key);
		}
	}

}
