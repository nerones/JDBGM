/**
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */

package com.thinkvirtual.sql.oracle;

import java.util.List;
import java.sql.*;

import com.crossdb.sql.*;
import com.crossdb.sql.optimization.OptimizationHint;
import com.crossdb.sql.optimization.FirstRowsOptimization;


public class OracleSelectQuery extends DefaultSelectQuery implements SelectQuery {


    public void addWhereCondition(String pre, int comparison, java.util.Date pred) {
        //Where where = new Where(pre, comparison, pred);
        SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
        wclause.addCondition(new WhereCondition(pre, comparison, " to_date('" + sqldf.format(pred) + "','YYYY-MM-DD HH24:MI:SS') "));
    }

    public void addWhereCondition(String and_or, String pre, int comparison, java.util.Date pred) {
        SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
        wclause.addCondition(and_or, new WhereCondition(pre, comparison, " to_date('" + sqldf.format(pred) + "','YYYY-MM-DD HH24:MI:SS') "));
    }


    public String toString() {
        StringBuffer ret = new StringBuffer(100);
        ret.append("SELECT ");
        if (isDistinct()) {
            ret.append("DISTINCT ");
        }
        for (int i = 0; i < optimizationHints.size(); i++) {
            OptimizationHint optimizationHint = (OptimizationHint) optimizationHints.get(i);
            if (optimizationHint instanceof FirstRowsOptimization) {
                FirstRowsOptimization fro = (FirstRowsOptimization) optimizationHint;
                ret.append("/*+ FIRST_ROWS(").append(fro.getN()).append(") */ ");
            }
        }
        int i;
        if (columns == null || columns.size() == 0) {
            ret.append("* ");
        } else {
            // rifle through columns and spit out string'
            int colsSizeMinus1 = columns.size() - 1;
            for (i = 0; i < columns.size(); i++) {
                String column = (String) (columns.get(i));
                ret.append(column);
                if (i < colsSizeMinus1) {
                    ret.append(",");
                }
            }
        }
        ret.append(" FROM ");
        StringBuffer join_conditions = new StringBuffer();
        if (tables == null || tables.size() == 0) {
            return null;
        } else {
            // rifle through tables and return string
            for (i = 0; i < tables.size(); i++) {
                Object tj = tables.get(i);
                if (tj instanceof String) {
                    String tablestr = (String) (tj);
                    if (i == 0) {
                        ret.append(tablestr);
                    } else {
                        ret.append(",").append(tablestr);
                    }
                } else if (tj instanceof Join) {
                    // extract info from join
                    Join join = (Join) (tj);
                    ret.append(",").append(join.getTableName()); // inner join default;
                    WhereClause jconds2 = join.getConditions();
                    List jconds = jconds2.getConditions();
                    for (int m = 0; m < jconds.size(); m++) {
                        WhereCondition c = (WhereCondition) (jconds.get(m));
                        if (m > 0 || join_conditions.length() > 0) {
                            join_conditions.append(" AND ");
                        }
                        if (join.getType() == Join.LEFT_OUTER_JOIN) {
                            /* This needs to use the (+) notation
                                    http://larr.unm.edu/~owen/SQLBOL70/html/2_005_28.htm
                                    http://otn.oracle.com/docs/products/oracle8i/doc_library/817_doc/server.817/a85397/state21b.htm#2065648
                                    LEFT OUTER JOIN Syntax is incorrect
                                    */
                            join_conditions.append(c.getPre()).append(WhereClause.getOperatorString(c.getOperator())).append(c.getPost()).append(" (+) ");
                        } else {
                            join_conditions.append(c.getPre()).append(WhereClause.getOperatorString(c.getOperator())).append(c.getPost());
                        }
                    }

                }

            }

        }

        if (wclause.hasConditions()) {
            ret.append(" WHERE ");
            // rifle through tables and return string
            ret.append(OracleWhereFormat.format(wclause));

        } else if (join_conditions.length() > 0) {
            ret.append(" WHERE ").append(join_conditions); // in case it's only join conditions;
        }
        if (join_conditions.length() > 0) {
            ret.append(" AND ").append(join_conditions);
        }
        if (group_by == null || group_by.size() == 0) {
            //return null;
        } else {
            ret.append(" GROUP BY ");
            // rifle through tables and return string
            int gbsizeMinus1 = group_by.size() - 1;
            for (i = 0; i < group_by.size(); i++) {
                String group = (String) (group_by.get(i));
                ret.append(group);
                if(i < gbsizeMinus1){
                    ret.append(",");
                }
            }

        }
        if (order_by == null || order_by.size() == 0) {
        } else {
            ret.append(" ORDER BY ");
            // rifle through tables and return string
            int obsizeMinus1 = order_by.size() -1;
            for (i = 0; i < order_by.size(); i++) {
                String order = (String) (order_by.get(i));
                ret.append(order);
                if(i < obsizeMinus1){
                    ret.append(",");
                }
            }

        }
        /*NEED ANOTHER WAY TO DO THIS, CAUSE ORACLE DOESN'T SUPPORT THIS
        could implement in resultset?
           if(limit != null){
           if(limit.length == 1){
           ret.append( " LIMIT " + limit[0];
           }
           else{ // length = 2
           ret.append( " LIMIT " + limit[0] + "," + limit[1];
           }
           }*/
        return ret.toString();
    }

    /*
    public CrossdbResultSet execute(Statement stmt) throws SQLException {
        //q = new 	Query(conn);
        //rs = stmt.executeQuery(querystring);
        return new OracleResultSet(stmt.executeQuery(toString()));
    }
    */


}
