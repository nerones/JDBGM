/**
 * 
 * @author Travis reeder
 * Date: Jan 27, 2003
 * Time: 3:46:01 PM
 * @version 0.1
 */
package com.thinkvirtual.sql.oracle;

import com.crossdb.sql.WhereCondition;
import com.crossdb.sql.SQLDateTimeFormat;
import com.crossdb.sql.WhereClause;

import java.util.List;

public class OracleWhereFormat {


    static SQLDateTimeFormat sqldf = null;



     public static String formatWhereCondition(WhereCondition cond){
        String ret = "";
                    if(cond.getPreTable() != null){
                        ret += cond.getPreTable() + ".";
                    }
                    Object pre = cond.getPre();
                    // check to see if object is a certain type for formatting
                    if(pre instanceof java.util.Date){
                        if(sqldf == null) sqldf = new SQLDateTimeFormat();

                        ret += " to_date('" + sqldf.format((java.util.Date)pre) + "','YYYY-MM-DD HH24:MI:SS') ";//'" + sqldf.format((java.util.Date)pre) + "' ";
                    }
                    else ret += pre + " ";

                    ret += " " + WhereClause.getOperatorString(cond.getComparison()) + " ";
                    if(cond.getPostTable() != null){
                        ret += cond.getPostTable() + ".";
                    }
                    pre = cond.getPost();
                    // check to see if object is a certain type for formatting
                    if(pre instanceof java.util.Date){
                        //System.out.println("YES");
                        if(sqldf == null) sqldf = new SQLDateTimeFormat();

                        ret += " to_date('" + sqldf.format((java.util.Date)pre) + "','YYYY-MM-DD HH24:MI:SS') ";///ret += "'" + sqldf.format((java.util.Date)pre) + "' ";
                    }
                    else ret += pre + " ";
        return ret;
    }

    public static String format(WhereClause wclause) {
        String ret = "";
            List conditions = wclause.getConditions();
            List separators = wclause.getSeparators();
            for(int i2 =0; i2 < conditions.size(); i2++){
                Object ob = conditions.get(i2);
                if(i2 != 0){
                    ret += separators.get(i2) + " ";
                }
                if(ob instanceof WhereCondition){
                    WhereCondition cond = (WhereCondition)ob;
                    ret += OracleWhereFormat.formatWhereCondition(cond );

                }
                else{ // clause cause that's all it can be
                    WhereClause clause = (WhereClause)ob;

                    ret += " (" + OracleWhereFormat.format(clause) + ") "; //" (" + clause.toString() + ") ";

                }

            }
        return ret;
    }
}
