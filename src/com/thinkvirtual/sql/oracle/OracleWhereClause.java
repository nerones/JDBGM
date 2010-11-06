package com.thinkvirtual.sql.oracle;

import com.crossdb.sql.WhereClause;
import com.crossdb.sql.WhereCondition;
import com.crossdb.sql.SQLFormat;

/**
 *
 * going to cast all the LIKE statements to upper() so that it will act the same as other dbs
 * 
 * @author Travis Reeder - travis@spaceprogram.com
 * Date: Apr 24, 2003
 * Time: 9:30:40 PM
 * @version 0.1
 */
public class OracleWhereClause extends WhereClause{
    public void addWhereLikeAny(String and_or, String col, String toCompare) {
        addCondition(and_or, new WhereCondition("upper(" + col+ ")", WhereCondition.LIKE, "upper('%" + SQLFormat.escape(toCompare) + "%')"));
    }

    /**
     * Will compare toCompare to the value in col where the left side is the same
     * @param col
     * @param toCompare
     */
    public void addWhereLikeLeft(String col, String toCompare) {
       addWhereLikeLeft("AND", col, toCompare);
    }

    public void addWhereLikeLeft(String and_or, String col, String toCompare) {
        addCondition(and_or, new WhereCondition("upper(" + col+ ")", WhereCondition.LIKE, "upper('" + SQLFormat.escape(toCompare) + "%')"));
    }

    /**
     * Will compare toCompare to the value in col where the right side is the same
     * @param col
     * @param toCompare
     */
    public void addWhereLikeRight(String col, String toCompare) {
        addWhereLikeRight("AND", col, toCompare);
    }

    public void addWhereLikeRight(String and_or, String col, String toCompare) {
        addCondition(and_or, new WhereCondition("upper(" + col+ ")", WhereCondition.LIKE, "upper('%" + SQLFormat.escape(toCompare) + "')"));
    }

    /**
     * this will compare the string to anywhere in the string
     * @param col
     * @param toCompare
     */
    public void addWhereLikeAny(String col, String toCompare) {
        addWhereLikeAny("AND", col, toCompare);
    }

}

