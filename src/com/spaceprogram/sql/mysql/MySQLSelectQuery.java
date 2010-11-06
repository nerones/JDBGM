package com.spaceprogram.sql.mysql;

/** This is an initial beta of a class that will represent a query string */

import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import com.crossdb.sql.*;
import com.crossdb.sql.ext.DefaultLevel1SelectQuery;
import com.crossdb.sql.ext.Level1SelectQuery;

public class MySQLSelectQuery extends DefaultLevel1SelectQuery implements Level1SelectQuery {


	public MySQLSelectQuery(){
		super();
	}

	public String toString(){
		StringBuffer ret = new StringBuffer(1024);
        ret.append("SELECT ");
        if(isDistinct()){
            ret.append("DISTINCT ");
        }
		int i;
		if(columns == null || columns.size() == 0){
			ret.append("* ");
		}
		else{
			// rifle through columns and spit out string'
			for(i = 0; i < columns.size(); i++){
                if(i > 0){
                    ret.append(",");
                }
				String column = (String)(columns.get(i));
				ret.append(column);

			}
			/*if(i > 0){
				ret = ret.substring(0,ret.length() -1);
			}*/
		}
		ret.append(" FROM ");
		String join_conditions = "";
		if(tables == null || tables.size() == 0){
			// try auto join

			Join.autoJoin(wclause, this);
            if(tables == null  || tables.size() == 0){
				return null;
			}
		}
		else{
			// rifle through tables and return string
			for(i = 0; i < tables.size(); i++){
				Object tj = tables.get(i);
				if(tj instanceof String){
					String tablestr = (String)(tj);
					if(i > 0){
                        ret.append(",");
					}
						ret.append(tablestr);
				}
				else if(tj instanceof Join){
					// extract info from join
					Join join = (Join)(tj);
					if(join.getType() == Join.LEFT_OUTER_JOIN){
						ret.append(" LEFT OUTER JOIN ").append(join.getTableName());
						WhereClause jconds = join.getConditions();

						if(jconds.hasConditions()){
							ret.append(" ON ");
						}
						/*for(int m = 0; m < jconds.size(); m++){
							WhereCondition c = (WhereCondition)(jconds.get(m));
							if(m > 0){
								ret.append(" AND ");
							}
							ret.append(c.getPre()).append(getOperatorString(c.getOperator())).append(c.getPost());
						}*/
                        ret.append(jconds.toString());
					}
					else{ // INNER JOIN
						ret.append(",").append(join.getTableName()); // inner join default
						WhereClause jconds = join.getConditions();

						/*for(int m = 0; m < jconds.size(); m++){
							WhereCondition c = (WhereCondition)(jconds.get(m));
							if(m > 0){
								join_conditions += ",";
							}
							join_conditions += c.getPre() + getOperatorString(c.getOperator()) + c.getPost();
						}*/
                        join_conditions = jconds.toString();
					}


				}

			}
			/*	if(i > 0){
			 ret = ret.substring(0,ret.length() -1);
			 }*/
		}

		if(wclause.hasConditions()){
			ret.append(" WHERE ");
			// rifle through tables and return string
			ret.append(wclause.toString());
		}
		else if(join_conditions.length() > 0){
			ret.append(" WHERE ").append(join_conditions);// in case it's only join conditions
		}
		if(join_conditions.length() > 0){
			ret.append(" AND ").append(join_conditions);
		}
		if(group_by == null || group_by.size() == 0){
			//return null;
		}
		else{
			ret.append(" GROUP BY ");
			// rifle through tables and return string
			for(i = 0; i < group_by.size(); i++){
                if(i > 0){
                    ret.append(",");
                }
				String group = (String)(group_by.get(i));
				ret.append(group);
			}

		}
		if(order_by == null || order_by.size() == 0){
			//return null;
		}
		else{
			ret.append(" ORDER BY ");
			// rifle through tables and return string
			for(i = 0; i < order_by.size(); i++){
                if(i > 0){
                    ret.append(",");
                }
				String order = (String)(order_by.get(i));
				ret.append(order);
			}

		}
		if(limit != null){
			if(limit.length == 1){
				ret.append(" LIMIT ").append(limit[0]);
			}
			else{ // length = 2
				ret.append(" LIMIT ").append(limit[0]).append(",").append(limit[1]);
			}
		}
		return ret.toString();
	}



}
